package cn.ponyzhang.server.service.redisson;

import cn.ponyzhang.server.dto.DeadDto;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonDelayQueuePublisher {
    private static final Logger logger = LoggerFactory.getLogger(RedissonDelayQueuePublisher.class);

    @Autowired
    private RedissonClient redissonClient;

    public void sendDelayMsg(final DeadDto msg,final Long ttl){
        try {
            final String delayQueueName = "redissonDelayQueueV3";
            RBlockingQueue<DeadDto> queue = redissonClient.getBlockingQueue(delayQueueName);
            RDelayedQueue<DeadDto> delayedQueue = redissonClient.getDelayedQueue(queue);
            delayedQueue.offer(msg,ttl, TimeUnit.MILLISECONDS);
            logger.info("阻塞队列的生产者-发送基本消息-发送消息成功：{}",msg);
        } catch (Exception exception) {
            logger.info("阻塞队列的生产者-发送基本消息-发送消息异常：{}",msg,exception.fillInStackTrace());
        }
    }
}
