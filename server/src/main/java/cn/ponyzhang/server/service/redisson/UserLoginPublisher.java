package cn.ponyzhang.server.service.redisson;

import cn.ponyzhang.server.dto.UserLoginDto;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserLoginPublisher {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginPublisher.class);

    private static final String key = "redissonUserLoginTopicKey";

    @Autowired
    private RedissonClient redissonClient;

    public void sendMsg(UserLoginDto dto){
        try {
            if(dto != null){
                logger.info("登录成功-生产者-发消息:{}",dto);
                RTopic topic = redissonClient.getTopic(key);
                topic.publish(dto);
            }
        } catch (Exception exception) {
            logger.error("登录成功-生产者-发生异常:{}",dto,exception.fillInStackTrace());
        }
    }
}
