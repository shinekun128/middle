package cn.ponyzhang.server.service.redisson;

import com.google.common.base.Strings;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer implements ApplicationRunner, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(QueueConsumer.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final String queueName = "redissonBasicQueue";
        RQueue<String> queue = redissonClient.getQueue(queueName);
        while (true) {
            String msg = queue.poll();
            if(!Strings.isNullOrEmpty(msg)){
                logger.info("队列的消费者-监听消息：{}",msg);
            }
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
