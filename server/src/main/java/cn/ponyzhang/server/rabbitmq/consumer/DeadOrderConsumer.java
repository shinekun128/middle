package cn.ponyzhang.server.rabbitmq.consumer;

import cn.ponyzhang.server.entity.UserOrder;
import cn.ponyzhang.server.mapper.UserOrderMapper;
import cn.ponyzhang.server.rabbitmq.entity.DeadInfo;
import cn.ponyzhang.server.service.rabbitmq.UserOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeadOrderConsumer {
    private static final Logger logger = LoggerFactory.getLogger(DeadOrderConsumer.class);

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private UserOrderService userOrderService;

    @RabbitListener(queues = "${mq.consumer.order.queue.name}",containerFactory = "singleListenerContainer")
    public void consumerMsg(@Payload Integer orderId){
        try {
            logger.info("用户下单支付超时-监听真正的队列-内容为：{}",orderId);
            //该业务只关注超时未付款，用户取消订单由其他业务组成
            UserOrder userOrder = userOrderMapper.selectByIdAndStatus(orderId, 1);
            if(userOrder != null){
                userOrderService.updateUserOrderRecord(userOrder);
            }
        } catch (Exception exception) {
            logger.info("用户下单支付超时-监听真正的队列-发生异常：{}",exception.fillInStackTrace());
        }
    }
}
