package cn.ponyzhang.server.controller.rabbitmq;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.controller.redis.CachePassController;
import cn.ponyzhang.server.dto.UserOrderDto;
import cn.ponyzhang.server.service.rabbitmq.UserOrderService;
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
public class UserOrderController {
    private static final Logger logger = LoggerFactory.getLogger(UserOrderController.class);

    private static final String prefix = "user/order";

    @Autowired
    private UserOrderService userOrderService;

    @RequestMapping(value = prefix+"/push",method = RequestMethod.POST)
    public BaseResponse userOrder(@Validated @RequestBody UserOrderDto userOrderDto, BindingResult result){
        BaseResponse response = null;
        if(result.hasErrors()){
            response = new BaseResponse(StatusCode.InvalidParams);
            response.setData("参数不合法，请重新输入");
            return response;
        }
        userOrderService.pushUserOrderRecord(userOrderDto);
        response = new BaseResponse(StatusCode.SUCCESS);
        response.setData("下单成功，请尽快支付");
        return response;
    }
}
