package cn.ponyzhang.server.event;

import cn.ponyzhang.server.service.redis.impl.IRedPacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class Consumer implements ApplicationListener<LoginEvent> {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Async
    @Override
    public void onApplicationEvent(LoginEvent event) {
        logger.info("spring事件驱动模型-接受消息：{}",event);
    }
}
