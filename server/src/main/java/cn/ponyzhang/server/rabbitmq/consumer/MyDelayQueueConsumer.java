package cn.ponyzhang.server.rabbitmq.consumer;

import cn.ponyzhang.server.dto.DeadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MyDelayQueueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ModelConsumer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @RabbitListener(queues = "${mq.redisson.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload DeadDto msg){
        try {
            logger.info("消息模型-消费者-监听消费到的消息：{}",msg);
        } catch (Exception exception) {
            logger.info("消息模型-消费者-监听消费到的消息异常：{}",msg,exception.fillInStackTrace());
        }

    }
}
