package cn.ponyzhang.server.service.impl;

import cn.ponyzhang.server.controller.redis.CachePassController;
import cn.ponyzhang.server.dto.RedPacketDto;
import cn.ponyzhang.server.entity.RedDetail;
import cn.ponyzhang.server.entity.RedRecord;
import cn.ponyzhang.server.entity.RedRobRecord;
import cn.ponyzhang.server.mapper.RedDetailMapper;
import cn.ponyzhang.server.mapper.RedRecordMapper;
import cn.ponyzhang.server.mapper.RedRobRecordMapper;
import cn.ponyzhang.server.service.RedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@EnableAsync
public class IRedService implements RedService {

    private static final Logger logger = LoggerFactory.getLogger(IRedService.class);

    @Autowired
    private RedRecordMapper redRecordMapper;

    @Autowired
    private RedDetailMapper redDetailMapper;

    @Autowired
    private RedRobRecordMapper redRobRecordMapper;

    @Async
    @Override
    public void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception {
        RedRecord redRecord = new RedRecord();
        redRecord.setRedPacket(redId);
        redRecord.setAmount(new BigDecimal(dto.getAmount()).divide(new BigDecimal(100)));
        redRecord.setTotal(dto.getTotal());
        redRecord.setCreateTime(new Date());
        redRecord.setUserId(dto.getUserId());
        redRecordMapper.insert(redRecord);
        int primaryKey = redRecordMapper.selectPrimaryKeyByRedId(redId);
        RedDetail redDetail ;
        for(Integer i : list){
            redDetail = new RedDetail();
            redDetail.setAmount(BigDecimal.valueOf(i));
            redDetail.setCreateTime(new Date());
            redDetail.setRecordId(primaryKey);
            redDetailMapper.insert(redDetail);
        }
    }

    @Override
    public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {
        RedRobRecord redRobRecord = new RedRobRecord();
        redRobRecord.setAmount(amount);
        redRobRecord.setRedPacket(redId);
        redRobRecord.setUserId(userId);
        redRobRecord.setRobTime(new Date());
        redRobRecordMapper.insert(redRobRecord);
    }
}
