package cn.ponyzhang.server.service.redis;

import cn.ponyzhang.server.dto.RedPacketDto;

import java.math.BigDecimal;

public interface RedPacketService {
    String handOut(RedPacketDto dto) throws Exception;

    BigDecimal rob(String redId,Integer userId) throws Exception;
}
