package cn.ponyzhang.server.service;

import cn.ponyzhang.server.dto.RedPacketDto;

import java.math.BigDecimal;
import java.util.List;

public interface RedService {
    /**
     *
     * @param dto 前后端交互信息，包括用户id，红包金额，红包数量
     * @param redId 该红包唯一标识
     * @param list 随机数生成红包列表
     */
    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception;

    void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception;
}
