package cn.ponyzhang.server.service.praise.cache.impl;

import cn.ponyzhang.server.dto.BlogPraiseDto;
import cn.ponyzhang.server.dto.PraiseRankDto;
import cn.ponyzhang.server.mapper.PraiseMapper;
import cn.ponyzhang.server.service.praise.cache.IRedisService;
import cn.ponyzhang.server.util.BlogPraiseUtil;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedissonService implements IRedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedissonService.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private PraiseMapper praiseMapper;

    private static final String blogCacheKey = "RedisBlogPraiseMap";
    private static final String blogPraiseCount = "RedisBlogPraiseCountMap";

    @Override
    public void cachePraiseBlog(BlogPraiseDto dto, Byte status) {
//        String blogRedissonPraiseLockKey = new StringBuffer("blogRedissonPraiseLock").append(blogId).append(userId).append(status).toString();
//        RLock lock = redissonClient.getLock(blogRedissonPraiseLockKey);
        String praiseKey = BlogPraiseUtil.getUserPraiseKey(dto);
        String countKey = String.valueOf(dto.getBlogId());
        RMap<String, Integer> praiseMap = redissonClient.getMap(blogCacheKey);
        RMap<String, Integer> countMap = redissonClient.getMap(blogPraiseCount);
        if (1 == status) {
            praiseMap.put(praiseKey, 1);
            countMap.put(countKey, countMap.getOrDefault(countKey, 0) + 1);
        } else {
            praiseMap.remove(praiseKey);
            countMap.put(countKey, countMap.getOrDefault(countKey, 0) - 1);
        }
    }

    @Override
    public Integer getCacheTotalBlog(Integer blogId) {
        RMap<String, Integer> countMap = redissonClient.getMap(blogPraiseCount);
        Integer result = countMap.getOrDefault(String.valueOf(blogId), 0);
        return result;
    }

    /**
     * 将数据库中排号的队列放入缓存中保存
     */
    public void rankBlogPraise(){
        final String key = "praiseRankListKey";
        List<PraiseRankDto> praiseRank = praiseMapper.getPraiseRank();
        if(praiseRank != null || praiseRank.size() > 0){
            RList<PraiseRankDto> rList = redissonClient.getList(key);
            rList.clear();
            rList.addAll(praiseRank);
        }
    }

    /**
     * 获取排行榜
     */
    public List<PraiseRankDto> getBlogPraiseRank(){
        final String key = "praiseRankListKey";
        RList<PraiseRankDto> list = redissonClient.getList(key);
        return list.readAll();
    }
}
