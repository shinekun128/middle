package cn.ponyzhang.server.rabbitmq.producer;

import cn.ponyzhang.server.dto.DeadDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyDelayQueuePublisher {
    private static final Logger logger = LoggerFactory.getLogger(MyDelayQueuePublisher.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;


    public void sendDelayMsg(DeadDto dto,Long ttl){
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.redisson.routing.key.name"));
            rabbitTemplate.setExchange(environment.getProperty("mq.redisson.exchange.name"));
            rabbitTemplate.convertAndSend(dto, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,DeadDto.class);
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setExpiration(String.valueOf(ttl));
                    return message;
                }
            });
            logger.info("生产者-发送消息入延迟队列-消息：{}",dto);
        } catch (AmqpException e) {
            logger.info("生产者-发送消息入延迟队列-异常：{}",e.fillInStackTrace());
        }


    }

}
