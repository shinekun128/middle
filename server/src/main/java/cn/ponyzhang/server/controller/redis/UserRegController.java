package cn.ponyzhang.server.controller.redis;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.UserRegDto;
import cn.ponyzhang.server.service.redis.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegController {

    private static final String prefix = "user";

    @Autowired
    private UserRegService userRegService;

    @RequestMapping(value = prefix+"/reg",method = RequestMethod.POST)
    public BaseResponse reg(@Validated @RequestBody UserRegDto dto, BindingResult result){
        BaseResponse response = null;
        if(result.hasErrors()){
            response = new BaseResponse(StatusCode.InvalidParams);
            response.setData("无效的参数，请重试");
            return response;
        }
//        response = userRegService.reg(dto);
        userRegService.regByRedis(dto);
        return response;
    }
}
