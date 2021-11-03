package cn.ponyzhang.server.service.redis.impl;

import cn.ponyzhang.server.controller.redis.CachePassController;
import cn.ponyzhang.server.entity.Item;
import cn.ponyzhang.server.mapper.ItemMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CachePassService {

    private static final Logger logger = LoggerFactory.getLogger(CachePassController.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String keyPrefix = "item:";

    public Item getItemInfo(String code) throws JsonProcessingException {
        Item item = null;
        String key = keyPrefix + code;
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)){
            logger.info("获取商品详情-缓存中存在该商品，商品编号为：{}",code);
            Object o = opsForValue.get(key);
            if(o != null && !Strings.isNullOrEmpty(o.toString())){
                Item readValue = objectMapper.readValue(o.toString(), Item.class);
            }
        }else{
            logger.info("获取商品详情-缓存中不存在该商品，从数据库中查询，商品编号为：{}",code);
            item = itemMapper.selectByCode(code);
            if(item != null){
                opsForValue.set(key,objectMapper.writeValueAsString(item));
            }else{
                opsForValue.set(key,"",30l, TimeUnit.MINUTES);
            }
        }
        return item;
    }

}
