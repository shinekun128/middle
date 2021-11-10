package cn.ponyzhang.server.rabbitmq.consumer;

import cn.ponyzhang.server.rabbitmq.entity.KnowledgeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KnowledgeConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.auto.knowledge.queue.name}",containerFactory = "singleListenerContainerAuto")
    public void consumeAutoMsg(@Payload KnowledgeInfo info){
        try {
            logger.info("autoAck消息模型-消费者-监听消费到的消息：{}",info);
        } catch (Exception exception) {
            logger.info("autoAck消息模型-消费者-监听消费到的消息异常：{}",exception.fillInStackTrace());
        }
    }
}
