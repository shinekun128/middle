package cn.ponyzhang.server.controller.redisson;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.DeadDto;
import cn.ponyzhang.server.rabbitmq.producer.MyDelayQueuePublisher;
import cn.ponyzhang.server.service.redisson.QueuePublisher;
import cn.ponyzhang.server.service.redisson.RedissonDelayQueuePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueController {
    private static final Logger logger = LoggerFactory.getLogger(QueueController.class);

    private static final String prefix = "queue";

    @Autowired
    private QueuePublisher queuePublisher;

    @Autowired
    private MyDelayQueuePublisher publisher;

    @RequestMapping(value = prefix+"/send/{msg}",method = RequestMethod.GET)
    public BaseResponse sendBasicMsg(@PathVariable("msg") String msg){
        BaseResponse response = null;

        try {
            queuePublisher.sendBasicMsg(msg);
            response = new BaseResponse(StatusCode.SUCCESS);
            response.setData("发送成功，消息是："+msg);
        } catch (Exception exception) {
            response = new BaseResponse(StatusCode.FAIL);
            response.setData("发送异常");
        }
        return response;
    }

    @RequestMapping(value = prefix+"/send/delay",method = RequestMethod.GET)
    public BaseResponse sendDelayMsg(){
        BaseResponse response = null;
        try {
            DeadDto dtoA = new DeadDto(1, "A");
            Long ttlA = 10000L;
            DeadDto dtoB = new DeadDto(2, "B");
            Long ttlB = 2000L;
            DeadDto dtoC = new DeadDto(3, "C");
            Long ttlC = 5000L;
            publisher.sendDelayMsg(dtoA,ttlA);
            publisher.sendDelayMsg(dtoB,ttlB);
            publisher.sendDelayMsg(dtoC,ttlC);
            response = new BaseResponse(StatusCode.SUCCESS);
            response.setData("发送成功，消息是");
        } catch (Exception exception) {
            response = new BaseResponse(StatusCode.FAIL);
            response.setData("发送异常");
        }
        return response;
    }

    @Autowired
    private RedissonDelayQueuePublisher redissonDelayQueuePublisher;

    @RequestMapping(value = prefix+"/redisson/msg/delay",method = RequestMethod.GET)
    public BaseResponse sendRedissonDelayMsg(){
        BaseResponse response = null;
        try {
            DeadDto dtoA = new DeadDto(1, "A");
            Long ttlA = 10000L;
            DeadDto dtoB = new DeadDto(2, "B");
            Long ttlB = 2000L;
            DeadDto dtoC = new DeadDto(3, "C");
            Long ttlC = 5000L;
            redissonDelayQueuePublisher.sendDelayMsg(dtoA,ttlA);
            redissonDelayQueuePublisher.sendDelayMsg(dtoB,ttlB);
            redissonDelayQueuePublisher.sendDelayMsg(dtoC,ttlC);
            response = new BaseResponse(StatusCode.SUCCESS);
            response.setData("发送成功");
        } catch (Exception exception) {
            response = new BaseResponse(StatusCode.FAIL);
            response.setData("发送异常");
        }
        return response;
    }


}
