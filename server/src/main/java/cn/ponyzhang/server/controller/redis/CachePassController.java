package cn.ponyzhang.server.controller.redis;

import cn.ponyzhang.server.entity.Item;
import cn.ponyzhang.server.service.redis.impl.CachePassService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CachePassController {
    private static final Logger logger = LoggerFactory.getLogger(CachePassController.class);

    private static final String prefix = "cache/pass";

    @Autowired
    private CachePassService cachePassService;

    @RequestMapping(value = prefix + "/item/info/{code}",method = RequestMethod.GET)
    public Map<String,Object> getItem(@PathVariable("code") String code) throws JsonProcessingException {
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("code",0);
        resMap.put("msg","成功");
        Item item = cachePassService.getItemInfo(code);
        resMap.put("data",item);
        return resMap;
    }


}
