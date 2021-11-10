package cn.ponyzhang.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    public void sendMsg() throws Exception{
        LoginEvent loginEvent = new LoginEvent(this,
                "ponyzhang",
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),
                "127.0.0.1");
        publisher.publishEvent(loginEvent);
        logger.info("spring事件驱动模型发送消息：{}",loginEvent);
    }
}
