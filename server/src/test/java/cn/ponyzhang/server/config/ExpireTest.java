package cn.ponyzhang.server.config;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ExpireTest {

    private static final Logger log = LoggerFactory.getLogger(ExpireTest.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void one() throws InterruptedException {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        String key = "redis:expire:1";
        opsForValue.set(key,"redis过期key测试",10, TimeUnit.SECONDS);
        Thread.sleep(5000);
        Boolean hasKey = redisTemplate.hasKey(key);
        Object o = opsForValue.get(key);
        log.info("等待5s，判断key是否存在：{}，key值是{}",hasKey,o);
        Thread.sleep(5000);
        hasKey = redisTemplate.hasKey(key);
        o = opsForValue.get(key);
        log.info("等待10s，判断key是否存在：{}，key值是{}",hasKey,o);
    }
    @Test
    public void two() throws InterruptedException {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        String key = "redis:expire:2";
        opsForValue.set(key,"redis过期key测试");
        redisTemplate.expire(key,10,TimeUnit.SECONDS);
        Thread.sleep(5000);
        Boolean hasKey = redisTemplate.hasKey(key);
        Object o = opsForValue.get(key);
        log.info("等待5s，判断key是否存在：{}，key值是{}",hasKey,o);
        Thread.sleep(5000);
        hasKey = redisTemplate.hasKey(key);
        o = opsForValue.get(key);
        log.info("等待10s，判断key是否存在：{}，key值是{}",hasKey,o);
    }
}
