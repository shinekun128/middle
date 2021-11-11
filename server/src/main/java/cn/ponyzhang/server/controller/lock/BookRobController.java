package cn.ponyzhang.server.controller.lock;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.BookRobDto;
import cn.ponyzhang.server.service.lock.BookRobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRobController {

    @Autowired
    private BookRobService bookRobService;

    @RequestMapping(value = "book/rob",method = RequestMethod.POST)
    public BaseResponse bookRob(@Validated @RequestBody BookRobDto dto, BindingResult result){
        BaseResponse response = null;
        if(result.hasErrors()){
            response = new BaseResponse(StatusCode.InvalidParams);
            response.setData("无效的参数，请重试");
            return response;
        }
//        response = bookRobService.bookRob(dto);
        response = bookRobService.bookRobWithLock(dto);
        return response;
    }
}
