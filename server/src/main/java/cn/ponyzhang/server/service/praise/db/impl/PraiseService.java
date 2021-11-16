package cn.ponyzhang.server.service.praise.db.impl;

import cn.ponyzhang.server.dto.BlogPraiseDto;
import cn.ponyzhang.server.dto.PraiseRankDto;
import cn.ponyzhang.server.entity.Praise;
import cn.ponyzhang.server.mapper.BlogMapper;
import cn.ponyzhang.server.mapper.PraiseMapper;
import cn.ponyzhang.server.service.praise.cache.impl.RedissonService;
import cn.ponyzhang.server.service.praise.db.IPraiseService;
import cn.ponyzhang.server.util.BlogPraiseUtil;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PraiseService implements IPraiseService {

    @Autowired
    private RedissonClient redissonClient;

    private static final String blogCacheKey = "RedisBlogPraiseMap";

    private static final String keyLock = "RedisBlogPraiseAddLock";

    @Autowired
    private PraiseMapper praiseMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedissonService redissonService;


    @Override
    public void addPraise(BlogPraiseDto dto) {
        RMap<String, Integer> blogCacheMap = redissonClient.getMap(blogCacheKey);
        String userPraiseKey = BlogPraiseUtil.getUserPraiseKey(dto);
        String blogRedissonPraiseLockKey = new StringBuffer("blogRedissonPraiseLock").append(dto.getBlogId()).append(dto.getUserId()).toString();
        RLock lock = redissonClient.getLock(blogRedissonPraiseLockKey);
        try {
            lock.tryLock(100,10,TimeUnit.SECONDS);
            if (!blogCacheMap.containsKey(userPraiseKey)) {
                Praise praise = praiseMapper.selectByUserIdAndBlogId(dto.getUserId(),dto.getBlogId());
                if (praise == null) {
                    //添加点赞到数据库和缓存
                    praise = new Praise();
                    praise.setBlogId(dto.getBlogId());
                    praise.setPraiseTime(new Date());
                    praise.setCreateTime(new Date());
                    praise.setStatus((byte) 1);
                    praise.setUserId(dto.getUserId());
                    praise.setIsActive((byte) 1);
                    int res1 = praiseMapper.insert(praise);
                    //更新blog表
                    int res2 = blogMapper.addByPrimaryKey(dto.getBlogId(), new Date());
                    //更新缓存
                    if (res1 > 0 && res2 > 0) {
                        redissonService.cachePraiseBlog(dto, new Byte((byte) 1));
                        cacheBlogPraise();
                    }
                } else {
                    //重新点赞
                    int res3 = praiseMapper.updateByUserId(dto.getUserId(), new Byte((byte) 1), new Date());
                    int res4 = blogMapper.addByPrimaryKey(dto.getBlogId(), new Date());
                    if (res3 > 0 && res4 > 0) {
                        redissonService.cachePraiseBlog(dto, new Byte((byte) 1));
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock.isLocked() && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    @Override
    public void addPraiseByLock(BlogPraiseDto dto) {
        final String lockName = keyLock + dto.getBlogId() + "-" + dto.getUserId();
        RLock lock = redissonClient.getLock(lockName);
        try {
            lock.lock(10L, TimeUnit.SECONDS);
            addPraise(dto);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    @Override
    public void cancelPraise(BlogPraiseDto dto) {
        String praiseKey = BlogPraiseUtil.getUserPraiseKey(dto);
        RMap<String, Integer> blogCacheMap = redissonClient.getMap(blogCacheKey);
        String blogRedissonPraiseLockKey = new StringBuffer("blogRedissonPraiseLock").append(dto.getBlogId()).append(dto.getUserId()).toString();
        RLock lock = redissonClient.getLock(blogRedissonPraiseLockKey);
        try {
            lock.tryLock(100,10,TimeUnit.SECONDS);
            if(blogCacheMap.containsKey(praiseKey)){
                //有一个执行失败应该回滚
                int res1 = praiseMapper.updateByUserId(dto.getUserId(), new Byte((byte) 0), new Date());
                int res2 = blogMapper.reduceByPrimaryKey(dto.getBlogId(), new Date());
                if(res1>0 && res2 > 0){
                    redissonService.cachePraiseBlog(dto,new Byte((byte) 0));
                    cacheBlogPraise();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock.isLocked()){
                lock.unlock();
            }
        }
    }

    @Override
    public Integer getBlogPraiseTotal(Integer blogId) {
        return redissonService.getCacheTotalBlog(blogId);
    }

    public Collection<PraiseRankDto> getRankWithRedisson(){
        return redissonService.getBlogPraiseRank();
    }

    /**
     * 通过数据库直接获取排行榜
     * @return
     */
    public Collection<PraiseRankDto> getRankWithNoRedisson(){
        return praiseMapper.getPraiseRank();
    }

    private void cacheBlogPraise(){
        redissonService.rankBlogPraise();
    }

    /**
     * 排序通过redis缓存的map
     * @return
     */
    public Collection<PraiseRankDto> getRankByRedisson(){
        final String blogPraiseCount = "RedisBlogPraiseCountMap";
        final String key = "praiseRankListKey";
        RMap<String, Integer> rMap = redissonClient.getMap(blogPraiseCount);
        List<PraiseRankDto> list = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : rMap.entrySet()){
            PraiseRankDto praiseRankDto = new PraiseRankDto();
            praiseRankDto.setBlogId(Integer.valueOf(entry.getKey()));
            praiseRankDto.setTotal(entry.getValue());
            list.add(praiseRankDto);
        }
        Collections.sort(list, new Comparator<PraiseRankDto>() {
            @Override
            public int compare(PraiseRankDto o1, PraiseRankDto o2) {
                return o2.getTotal()-o1.getTotal();
            }
        });
        if(list != null || list.size() > 0){
            RList<PraiseRankDto> rList = redissonClient.getList(key);
            rList.clear();
            rList.addAll(list);
        }
        return list;
    }
}
