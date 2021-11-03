package cn.ponyzhang.server.util;

import cn.ponyzhang.server.config.ExpireTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class RedPacketUtilTest {
    private static final Logger log = LoggerFactory.getLogger(RedPacketUtilTest.class);

    @Test
    void divideRedPacket() {
        int totalCount = 10;
        int totalAmount = 1000;
        List<Integer> redPacket = RedPacketUtil.divideRedPacket(totalAmount, totalCount);
        log.info("总金额{},红包个数{}",totalAmount,totalCount);
        int sum = 0;
        for(int redNum : redPacket){
            log.info("红包金额{}",new BigDecimal(redNum).divide(new BigDecimal(100)));
            sum+=redNum;
        }
        log.info("红包总额{}",sum);
    }
}