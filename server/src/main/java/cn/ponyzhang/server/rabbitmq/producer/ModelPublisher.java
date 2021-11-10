package cn.ponyzhang.server.rabbitmq.producer;

import cn.ponyzhang.server.rabbitmq.entity.EventInfo;
import cn.ponyzhang.server.rabbitmq.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class ModelPublisher {
    private static final Logger logger = LoggerFactory.getLogger(ModelPublisher.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMsg(EventInfo eventInfo){
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.fanout.exchange.name"));
            rabbitTemplate.convertAndSend(eventInfo, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, EventInfo.class);
                    return message;
                }
            });
            logger.info("fanout消息模型-生产者-发送消息：{}",eventInfo);
        } catch (Exception e) {
            logger.info("fanout消息模型-生产者-发送消息异常：{}",e.fillInStackTrace());
        }
    }

    public void sendTopicMsg(String msg,String routingKey){
        if(!Strings.isNullOrEmpty(msg) && !Strings.isNullOrEmpty(routingKey)){
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.topic.exchange.name"));
            rabbitTemplate.setRoutingKey(routingKey);
            rabbitTemplate.convertAndSend(msg);
            logger.info("Topic消息模型-生产者-发送消息：{}",msg);
        }
    }
}
