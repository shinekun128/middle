package cn.ponyzhang.server.service.redis.impl;

import cn.ponyzhang.server.dto.RedPacketDto;
import cn.ponyzhang.server.service.RedService;
import cn.ponyzhang.server.service.redis.RedPacketService;
import cn.ponyzhang.server.util.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IRedPacketService implements RedPacketService {

    private static final Logger logger = LoggerFactory.getLogger(IRedPacketService.class);

    private static final String prefix = "redis:red:packet:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedService redService;

    @Override
    public String handOut(RedPacketDto dto) throws Exception {
        if (dto.getAmount() > 0 && dto.getTotal() > 0) {
            String timestamp = String.valueOf(System.nanoTime());
            String redId = new StringBuilder(prefix).append(dto.getUserId()).append(":").append(timestamp).toString();
            List<Integer> redPacket = RedPacketUtil.divideRedPacket(dto.getAmount(), dto.getTotal());
            redisTemplate.opsForList().leftPushAll(redId, redPacket.toArray());
            String redTotalKey = redId + ":total";
            redisTemplate.opsForValue().set(redTotalKey, dto.getTotal());
            redService.recordRedPacket(dto, redId, redPacket);
            return redId;
        } else {
            throw new Exception("系统异常--分发红包--参数异常");
        }
    }

    @Override
    public BigDecimal rob(String redId, Integer userId) throws Exception {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //判断用户是否抢过红包
        Object o = opsForValue.get(redId + userId + ":rob");
        if (o != null) {
            return new BigDecimal(o.toString());
        }
        boolean res = click(redId);
        if (res) {
            Object redPacket = redisTemplate.opsForList().rightPop(redId);
            if (redPacket != null) {
                String totalKey = redId + ":total";
                Object redTotal = opsForValue.get(totalKey);
                opsForValue.set(totalKey, Integer.valueOf(redTotal.toString()) - 1);
                BigDecimal result = new BigDecimal(redPacket.toString()).divide(new BigDecimal(100));
                redService.recordRobRedPacket(userId, redId, result);
                opsForValue.set(redId +":" + userId + ":rob", result, 24L, TimeUnit.HOURS);
                return result;
            }
        }
        return null;
    }

    private boolean click(String redId) {
        String key = redId + ":total";
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        Object o = opsForValue.get(key);
        if (o != null && Integer.valueOf(o.toString()) > 0) {
            return true;
        }
        return false;
    }
}
