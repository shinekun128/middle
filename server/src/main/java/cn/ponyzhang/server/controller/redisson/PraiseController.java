package cn.ponyzhang.server.controller.redisson;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.BlogPraiseDto;
import cn.ponyzhang.server.service.praise.db.impl.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PraiseController {

    @Autowired
    private PraiseService praiseService;

    @RequestMapping(value = "praise/add",method = RequestMethod.POST)
    public BaseResponse addPraise(@Validated @RequestBody BlogPraiseDto dto, BindingResult result){
        BaseResponse response = null;
        if(result.hasErrors()){
            response = new BaseResponse(StatusCode.InvalidParams);
            response.setData("参数为空，请重新输入");
            return response;
        }
        praiseService.addPraise(dto);
        Integer total = praiseService.getBlogPraiseTotal(dto.getBlogId());
        response = new BaseResponse(StatusCode.SUCCESS);
        response.setData("当前博客id:"+dto.getBlogId()+",点赞总数:"+total);
        return response;
    }

    @RequestMapping(value = "praise/cancel",method = RequestMethod.POST)
    public BaseResponse cancelPraise(@Validated @RequestBody BlogPraiseDto dto, BindingResult result){
        BaseResponse response = null;
        if(result.hasErrors()){
            response = new BaseResponse(StatusCode.InvalidParams);
            response.setData("参数为空，请重新输入");
            return response;
        }
        praiseService.cancelPraise(dto);
        Integer total = praiseService.getBlogPraiseTotal(dto.getBlogId());
        response = new BaseResponse(StatusCode.SUCCESS);
        response.setData("当前博客id:"+dto.getBlogId()+",点赞总数:"+total);
        return response;
    }

    @RequestMapping(value = "praise/rank",method = RequestMethod.GET)
    public BaseResponse rankPraise(){
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        response.setData("缓存排行榜"+praiseService.getRankWithRedisson());
        return response;
    }

    @RequestMapping(value = "praise/rank/redis",method = RequestMethod.GET)
    public BaseResponse rankPraiseByRedis(){
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        response.setData("缓存排行榜"+praiseService.getRankByRedisson());
        return response;
    }
}
