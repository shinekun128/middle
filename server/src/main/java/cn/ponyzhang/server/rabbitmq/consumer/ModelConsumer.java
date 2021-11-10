package cn.ponyzhang.server.rabbitmq.consumer;

import cn.ponyzhang.server.rabbitmq.entity.EventInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ModelConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ModelConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;


    @RabbitListener(queues = "${mq.fanout.queue.one.name}",containerFactory = "singleListenerContainer")
    public void consumeMsgOne(@Payload EventInfo eventInfo){
        try {
            logger.info("fanout消息模型-消费者1-监听消费到的消息：{}",eventInfo);
        } catch (Exception exception) {
            logger.info("fanout消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.fanout.queue.two.name}",containerFactory = "singleListenerContainer")
    public void consumeMsgTwo(@Payload EventInfo eventInfo){
        try {
            logger.info("fanout消息模型-消费者2-监听消费到的消息：{}",eventInfo);
        } catch (Exception exception) {
            logger.info("fanout消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.topic.queue.one.name}",containerFactory = "singleListenerContainer")
    public void consumeTopicMsgOne(@Payload String msg){
        try {
            logger.info("topic消息模型-消费者1-路由*-监听消费到的消息：{}",msg);
        } catch (Exception exception) {
            logger.info("基本消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.topic.queue.two.name}",containerFactory = "singleListenerContainer")
    public void consumeTopicMsgTwo(@Payload String msg){
        try {
            logger.info("topic消息模型-消费者2-路由*-监听消费到的消息：{}",msg);
        } catch (Exception exception) {
            logger.info("基本消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.topic.queue.three.name}",containerFactory = "multiListenerContainer")
    public void consumeTopicMsgThree(@Payload String msg){
        try {
            logger.info("topic消息模型-消费者3-路由#-监听消费到的消息：{}",msg);
        } catch (Exception exception) {
            logger.info("基本消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.topic.queue.four.name}",containerFactory = "multiListenerContainer")
    public void consumeTopicMsgFour(@Payload String msg){
        try {
            logger.info("topic消息模型-消费者4-路由#-监听消费到的消息：{}",msg);
        } catch (Exception exception) {
            logger.info("基本消息模型-消费者-发生异常:{}",exception.fillInStackTrace());
        }
    }
}
