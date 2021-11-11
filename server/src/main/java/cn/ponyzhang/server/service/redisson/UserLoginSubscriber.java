package cn.ponyzhang.server.service.redisson;

import cn.ponyzhang.server.dto.UserLoginDto;
import cn.ponyzhang.server.service.rabbitmq.SysLogService;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class UserLoginSubscriber implements ApplicationRunner, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginSubscriber.class);

    private static final String key = "redissonUserLoginTopicKey";

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            RTopic rTopic = redissonClient.getTopic(key);
            rTopic.addListener(UserLoginDto.class, new MessageListener<UserLoginDto>() {
                @Override
                public void onMessage(CharSequence charSequence, UserLoginDto userLoginDto) {
                    logger.info("记录登录成功之后的路径-消费者-监听消息：{}",userLoginDto);
                    if (userLoginDto != null){
                        sysLogService.recordLog(userLoginDto);
                    }
                }
            });
        } catch (Exception exception) {
            logger.info("记录登录成功之后的路径-消费者-监听消息异常：{}",exception.fillInStackTrace());
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
