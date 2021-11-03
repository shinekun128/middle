package cn.ponyzhang.server.controller.redis;


import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.RedPacketDto;
import cn.ponyzhang.server.service.redis.RedPacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class RedPacketController {

    private static final String prefix = "/redPacket";
    private static final Logger logger = LoggerFactory.getLogger(RedPacketController.class);

    @Autowired
    private RedPacketService redPacketService;

    @RequestMapping(value = prefix + "/handout",method = RequestMethod.POST)
    public BaseResponse redPacketController(@Validated @RequestBody RedPacketDto dto, BindingResult result) throws Exception {
        BaseResponse response = null;
        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            List<String> collect = fieldErrors.stream().map(o -> o.getDefaultMessage()).collect(Collectors.toList());
            response = new BaseResponse(StatusCode.InvalidParams);
            response.setData(collect);
            return response;
        }
        response = new BaseResponse(StatusCode.SUCCESS);
        String redId = redPacketService.handOut(dto);
        response.setData(redId);
        return response;
    }

    @RequestMapping(value = prefix + "/rob/{userId}/{redId}",method = RequestMethod.GET)
    public BaseResponse<Object> redPacketRobController(@PathVariable("userId") Integer userId,@PathVariable("redId") String redId){
        BaseResponse<Object> response ;
        try {
            BigDecimal result = redPacketService.rob(redId, userId);
            if(result != null){
                response = new BaseResponse<>(StatusCode.SUCCESS);
                response.setData(result);
            }else{
                response = new BaseResponse<>(StatusCode.FAIL);
                response.setData("红包已经被抢完");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            response = new BaseResponse<>(StatusCode.FAIL);
            response.setData(exception.getMessage());
        }
        return response;
    }
}
