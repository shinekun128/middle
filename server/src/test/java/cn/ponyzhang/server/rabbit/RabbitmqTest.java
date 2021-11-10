package cn.ponyzhang.server.rabbit;

import cn.ponyzhang.server.entity.UserOrder;
import cn.ponyzhang.server.mapper.UserOrderMapper;
import cn.ponyzhang.server.rabbitmq.entity.DeadInfo;
import cn.ponyzhang.server.rabbitmq.entity.EventInfo;
import cn.ponyzhang.server.rabbitmq.entity.KnowledgeInfo;
import cn.ponyzhang.server.rabbitmq.entity.Person;
import cn.ponyzhang.server.rabbitmq.consumer.BasicConsumer;
import cn.ponyzhang.server.rabbitmq.producer.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitmqTest {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqTest.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BasicPublisher basicPublisher;

    @Autowired
    private ModelPublisher modelPublisher;

    @Test
    public void one(){
        String msg = "这是一段字符串信息";
        basicPublisher.sendMsg(msg);
    }

    @Test
    public void two(){
        Person p = new Person(1,"小鸡","chicken");
        basicPublisher.sendObjectMsg(p);
    }

    @Test
    public void three(){
        EventInfo eventInfo = new EventInfo(1,"模块","fanout消息模型","消息模型");
        modelPublisher.sendMsg(eventInfo);
    }

    @Test
    public void four(){
        String msg = "这是一条msg信息";
        String routingKeyOne = "local.middle.mq.topic.routing.java.key";
        String routingKeyTwo = "local.middle.mq.topic.routing.key";
        modelPublisher.sendTopicMsg(msg,routingKeyTwo);
    }
    @Autowired
    private KnowledgePublisher knowledgePublisher;
    @Test
    public void five(){
        KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
        knowledgeInfo.setCode("1234");
        knowledgeInfo.setMode("sf");
        knowledgeInfo.setId(1);
        knowledgePublisher.sendAutoMsg(knowledgeInfo);
    }

    @Autowired
    private KnowledgeManualPublisher knowledgeManualPublisher;

    @Test
    public void six(){
        KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
        knowledgeInfo.setCode("1234");
        knowledgeInfo.setMode("sf");
        knowledgeInfo.setId(1);
        knowledgeManualPublisher.sendManualMsg(knowledgeInfo);
    }
    @Autowired
    private DeadPublisher deadPublisher;

    @Test
    public void deadQueueTest(){
        DeadInfo info1 = new DeadInfo(1, "我是第一则消息");
        DeadInfo info2 = new DeadInfo(2, "我是第二则消息");
        try {
            deadPublisher.sendMsg(info1);
            deadPublisher.sendMsg(info2);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private UserOrderMapper userOrderMapper;

}
