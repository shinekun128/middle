package cn.ponyzhang.server.service.redisson;

import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueuePublisher {
    private static final Logger logger = LoggerFactory.getLogger(QueuePublisher.class);

    @Autowired
    private RedissonClient redissonClient;

    public void sendBasicMsg(String msg){
        try {
            final String queueName="redissonBasicQueue";
            RQueue<String> rQueue = redissonClient.getQueue(queueName);
            rQueue.add(msg);
            logger.info("队列的生产者-发送基本消息-发送消息成功：{}",msg);
        } catch (Exception exception) {
            logger.info("队列的生产者-发送基本消息-发送消息异常：{}",exception.fillInStackTrace());
        }
    }

}
