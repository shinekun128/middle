package cn.ponyzhang.server.rabbitmq.consumer;

import cn.ponyzhang.server.rabbitmq.entity.DeadInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeadConsumer {
    private static final Logger logger = LoggerFactory.getLogger(DeadConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.real.queue.name}",containerFactory = "singleListenerContainer")
    public void consumerMsg(@Payload DeadInfo deadInfo){
        try {
            logger.info("死信队列实战-监听真正的队列-内容为：{}",deadInfo);
        } catch (Exception exception) {
            logger.info("死信队列实战-监听真正的队列-内容为：{}",exception.fillInStackTrace());
        }
    }

}
