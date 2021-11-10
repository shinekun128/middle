package cn.ponyzhang.server.rabbitmq.consumer;

import cn.ponyzhang.server.rabbitmq.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BasicConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BasicConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;


    @RabbitListener(queues = "${mq.basic.info.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload String msg){
        try {
            logger.info("基本消息模型-消费者-监听消费到的消息：{}",msg);
        } catch (Exception exception) {
            logger.info("基本消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.object.info.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeObjectMsg(@Payload Person person){
        try {
            logger.info("基本消息模型-消费者-监听消费到的消息：{}",person);
        } catch (Exception exception) {
            logger.info("基本消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }
}
