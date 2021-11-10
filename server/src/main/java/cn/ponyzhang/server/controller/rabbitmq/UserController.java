package cn.ponyzhang.server.controller.rabbitmq;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.UserLoginDto;
import cn.ponyzhang.server.service.rabbitmq.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final String prefix = "user";

    @Autowired
    private UserService userService;

    @RequestMapping(value = prefix + "/login" , method = RequestMethod.POST)
    public BaseResponse login(@RequestBody @Validated UserLoginDto userLoginDto , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response;
        try {
            boolean res = userService.login(userLoginDto);
            if(res){
                response = new BaseResponse(StatusCode.SUCCESS);
                response.setData("登录成功");
            }else{
                response = new BaseResponse(StatusCode.FAIL);
                response.setData("用户名或密码错误");
            }
        } catch (Exception exception) {
            response = new BaseResponse(StatusCode.FAIL);
            response.setData(exception.getMessage());
        }
        return response;
    }
}
