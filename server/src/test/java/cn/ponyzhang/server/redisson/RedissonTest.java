package cn.ponyzhang.server.redisson;

import cn.ponyzhang.server.dto.RSetDto;
import cn.ponyzhang.server.dto.UserLoginDto;
import cn.ponyzhang.server.service.redisson.UserLoginPublisher;
import cn.ponyzhang.server.util.RSetComparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedissonTest {
    private static final Logger log = LoggerFactory.getLogger(RedissonTest.class);

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() throws IOException {
        log.info("redisson配置信息：{}",redissonClient.getConfig().toJSON());
    }

    @Test
    public void test2() throws IOException {
        final String key = "myBloomFilterDataV2";
        long total = 100000l;
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(total,0.01);
        for(int i=1;i<total;i++){
            bloomFilter.add(i);
        }
        log.info("bloomFilter中是否存在1{}",1);
        log.info("bloomFilter中是否存在10000{}",bloomFilter.contains(1));
        log.info("bloomFilter中是否存在-1{}",-1);
        log.info("bloomFilter中是否存在1000000{}",1000000);
    }

    @Autowired
    private UserLoginPublisher publisher;

    @Test
    public void test3(){
        UserLoginDto dto = new UserLoginDto();
        dto.setId(9001);
        dto.setUserName("z-k-c");
        dto.setPassword("abc123");
        publisher.sendMsg(dto);
    }


    @Test
    public void test4(){
        final String key = "myRedissonSortedSetV2";
        RSetDto dto1 = new RSetDto(1,"N1",20,10d);
        RSetDto dto2 = new RSetDto(2,"N2",18,2d);
        RSetDto dto3 = new RSetDto(3,"N3",21,8d);
        RSetDto dto4 = new RSetDto(4,"N4",19,6d);
        RSetDto dto5 = new RSetDto(5,"N5",22,1d);
        RSortedSet<RSetDto> sortedSet = redissonClient.getSortedSet(key);
        sortedSet.trySetComparator(new RSetComparator());
        sortedSet.add(dto1);
        sortedSet.add(dto2);
        sortedSet.add(dto3);
        sortedSet.add(dto4);
        sortedSet.add(dto5);
        Collection<RSetDto> rSetDtos = sortedSet.readAll();
        log.info("列表元素{}",rSetDtos);
    }

    @Test
    public void test5(){
        final String key = "myRedissonScoredSet";
        RSetDto dto1 = new RSetDto(1, "N1", 10.0D);
        RSetDto dto2 = new RSetDto(2, "N2", 2.0D);
        RSetDto dto3 = new RSetDto(3, "N3", 8.0D);
        RSetDto dto4 = new RSetDto(4, "N4", 6.0D);
        RScoredSortedSet<RSetDto> scoredSortedSet = redissonClient.getScoredSortedSet(key);
        scoredSortedSet.add(dto1.getScore(),dto1);
        scoredSortedSet.add(dto2.getScore(),dto2);
        scoredSortedSet.add(dto3.getScore(),dto3);
        scoredSortedSet.add(dto4.getScore(),dto4);
        Set<RSetDto> rSetDtos = scoredSortedSet.readSortAlpha(SortOrder.DESC);
        log.info("排序集合，从大到小",rSetDtos);
        log.info("对象排名，对象元素={},从大到小的排名={}",dto1,scoredSortedSet.revRank(dto1)+1);
        log.info("对象排名，对象元素={},从大到小的排名={}",dto2,scoredSortedSet.revRank(dto2)+1);
        log.info("对象排名，对象元素={},从大到小的排名={}",dto3,scoredSortedSet.revRank(dto3)+1);
        log.info("对象排名，对象元素={},从大到小的排名={}",dto4,scoredSortedSet.revRank(dto4)+1);

        log.info("对象排名，对象元素={},从大到小的排名={}",dto1,scoredSortedSet.getScore(dto1));
        log.info("对象排名，对象元素={},从大到小的排名={}",dto2,scoredSortedSet.getScore(dto2));
        log.info("对象排名，对象元素={},从大到小的排名={}",dto3,scoredSortedSet.getScore(dto3));
        log.info("对象排名，对象元素={},从大到小的排名={}",dto4,scoredSortedSet.getScore(dto4));

    }


}
