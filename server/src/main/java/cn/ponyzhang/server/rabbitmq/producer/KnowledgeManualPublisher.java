package cn.ponyzhang.server.rabbitmq.producer;

import cn.ponyzhang.server.rabbitmq.entity.KnowledgeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KnowledgeManualPublisher {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeManualPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendManualMsg(KnowledgeInfo info){
        if(info != null){
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.manual.knowledge.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.manual.knowledge.routing.key.name"));
                Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(message);
                logger.info("基于manual机制-生产者发送消息-内容为：{}",info);
            } catch (JsonProcessingException e) {
                logger.info("基于manual机制-生产者发送消息-异常：{}",e.fillInStackTrace());
            }
        }
    }

}
