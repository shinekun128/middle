package cn.ponyzhang.server.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EventTest {

    @Autowired
    private Producer producer;

    @Test
    public void testEvent() throws Exception {
        producer.sendMsg();
    }
}
