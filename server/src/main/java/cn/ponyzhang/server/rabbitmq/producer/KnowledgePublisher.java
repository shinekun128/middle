package cn.ponyzhang.server.rabbitmq.producer;

import cn.ponyzhang.server.rabbitmq.entity.KnowledgeInfo;
import cn.ponyzhang.server.rabbitmq.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KnowledgePublisher {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgePublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendAutoMsg(KnowledgeInfo info){
        if(info != null){
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.auto.knowledge.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.auto.knowledge.routing.key.name"));
                rabbitTemplate.convertAndSend(info, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, KnowledgeInfo.class);
                        return message;
                    }
                });
                logger.info("AutoAck消息模型-生产者-发送消息：{}",info);
            } catch (Exception e) {
                logger.info("AutoAck消息模型-生产者-发送消息异常：{}",e.fillInStackTrace());
            }

        }
    }
}
