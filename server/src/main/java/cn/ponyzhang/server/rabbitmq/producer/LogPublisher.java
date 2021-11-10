package cn.ponyzhang.server.rabbitmq.producer;

import cn.ponyzhang.server.dto.UserLoginDto;
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
public class LogPublisher {

    private static final Logger logger = LoggerFactory.getLogger(LogPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendLogMsg(UserLoginDto userLoginDto){
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.login.exchange.name"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.login.routing.key.name"));
            rabbitTemplate.convertAndSend(userLoginDto, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,UserLoginDto.class);
                    return message;
                }
            });
            logger.info("系统日志记录-生产者-将登录成功的相关消息发送给队列：{}",userLoginDto);
        } catch (AmqpException e) {
            logger.info("系统日志记录-生产者-将登录成功的相关消息发送给队列-异常：{}",e.fillInStackTrace());
        }
    }
}
