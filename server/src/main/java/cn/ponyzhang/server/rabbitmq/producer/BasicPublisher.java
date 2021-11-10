package cn.ponyzhang.server.rabbitmq.producer;

import cn.ponyzhang.server.rabbitmq.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
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
public class BasicPublisher {

    private static final Logger logger = LoggerFactory.getLogger(BasicPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMsg(String message){
        if(!Strings.isNullOrEmpty(message)){
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.basic.info.routing.key.name"));
                rabbitTemplate.setExchange(environment.getProperty("mq.basic.info.exchange.name"));
                rabbitTemplate.convertAndSend(message);
                logger.info("基本消息模型-生产者-发送消息：{}",message);
            } catch (Exception e) {
                logger.info("基本消息模型-生产者-发送消息异常：{}",e.fillInStackTrace());
            }
        }
    }

    public void sendObjectMsg(Person person){
        if(person != null){
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.object.info.exchange.name"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.object.info.routing.key.name"));
            rabbitTemplate.convertAndSend(person, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Person.class);
                    return message;
                }
            });
            logger.info("基本消息模型-生产者-发送消息：{}",person);
        }
    }
}
