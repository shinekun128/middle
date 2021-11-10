//package cn.ponyzhang.server.rabbitmq.consumer;
//
//import cn.ponyzhang.server.dto.UserLoginDto;
//import cn.ponyzhang.server.rabbitmq.entity.KnowledgeInfo;
//import cn.ponyzhang.server.service.rabbitmq.SysLogService;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LogConsumer {
//    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);
//
//    @Autowired
//    private SysLogService sysLogService;
//
//    @RabbitListener(queues = "${mq.login.queue.name}",containerFactory = "singleListenerContainerAuto")
//    public void consumeAutoMsg(@Payload UserLoginDto userLoginDto){
//        try {
//            logger.info("系统日志记录-消费者-监听消费用户登录成功后的消息-内容：{}",userLoginDto);
//            sysLogService.recordLog(userLoginDto);
//        } catch (Exception exception) {
//            logger.info("autoAck消息模型-消费者-监听消费到的消息异常：{}",exception.fillInStackTrace());
//        }
//    }
//}
