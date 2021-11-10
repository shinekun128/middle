package cn.ponyzhang.server.service.rabbitmq;


import cn.ponyzhang.server.dto.UserLoginDto;
import cn.ponyzhang.server.entity.SysLog;
import cn.ponyzhang.server.mapper.SysLogMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@EnableAsync
public class SysLogService {

    private static final Logger logger = LoggerFactory.getLogger(SysLogService.class);

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Async
    public void recordLog(UserLoginDto userLoginDto) {
        try {
            SysLog sysLog = new SysLog();
            sysLog.setUserId(userLoginDto.getId());
            sysLog.setModule("用户登录模块");
            sysLog.setMemo("用户登录成功记录相关信息");
            sysLog.setData(objectMapper.writeValueAsString(userLoginDto));
            sysLog.setCreateTime(new Date());
            sysLogMapper.insert(sysLog);
        } catch (JsonProcessingException e) {
            logger.info("系统日志服务-记录登录成功的相关消息-异常：{}",e.fillInStackTrace());
        }

    }
}
