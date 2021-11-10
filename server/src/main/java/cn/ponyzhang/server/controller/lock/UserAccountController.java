package cn.ponyzhang.server.controller.lock;


import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.UserAccountDto;
import cn.ponyzhang.server.service.lock.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {
    private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    private static final String prefix = "lock/db";

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = prefix+"/money",method = RequestMethod.POST)
    public BaseResponse updateAccount(@Validated @RequestBody UserAccountDto dto, BindingResult result){
        BaseResponse response = null;
        if(result.hasErrors() || dto.getMoney()<=0){
            response = new BaseResponse(StatusCode.InvalidParams);
            response.setData("参数有误");
            return response;
        }
        try {
            userAccountService.takeMoneyWithNegativeLock(dto);
            response = new BaseResponse(StatusCode.SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            response = new BaseResponse(StatusCode.FAIL);
        }
        return response;
    }
}
