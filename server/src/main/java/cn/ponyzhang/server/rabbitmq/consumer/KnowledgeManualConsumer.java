package cn.ponyzhang.server.rabbitmq.consumer;

import cn.ponyzhang.server.rabbitmq.entity.KnowledgeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("knowledgeManualConsumer")
public class KnowledgeManualConsumer implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeManualConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            byte[] messageBody = message.getBody();
            KnowledgeInfo knowledgeInfo = objectMapper.readValue(messageBody, KnowledgeInfo.class);
            logger.info("确认消费模式-手动确认-监听消息内容为：{}", knowledgeInfo);
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            logger.info("确认消费模式-手动确认-监听消息异常：{}", e.fillInStackTrace());
            channel.basicAck(deliveryTag, false);
        }
    }
}
