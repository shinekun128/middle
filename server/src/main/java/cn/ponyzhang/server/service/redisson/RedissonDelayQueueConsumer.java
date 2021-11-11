package cn.ponyzhang.server.service.redisson;

import cn.ponyzhang.server.dto.DeadDto;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RedissonDelayQueueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RedissonDelayQueueConsumer.class);
    @Autowired
    private RedissonClient redissonClient;

    @Scheduled(cron = "*/1 * * * * ?")
    public void consumerMsg() throws Exception{
        final String delayQueueName = "redissonDelayQueueV3";
        RBlockingQueue<DeadDto> queue = redissonClient.getBlockingQueue(delayQueueName);
        DeadDto dto = queue.take();
        if(dto != null){
            logger.info("阻塞队列的消费者-读取消息：{}",dto);
        }
    }
}
