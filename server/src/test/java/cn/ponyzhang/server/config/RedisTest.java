package cn.ponyzhang.server.config;


import cn.ponyzhang.server.config.entity.Fruit;
import cn.ponyzhang.server.config.entity.PhoneUser;
import cn.ponyzhang.server.config.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void one(){
        logger.info("---redistemplate测试---");
        final String content = " redisTemplate字符串信息";
        final String key = "redis:template:one:string";
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        logger.info("写入缓存内存 {}" ,content);
        stringObjectValueOperations.set(key,content);
        String result = (String)stringObjectValueOperations.get(key);
        logger.info("读出内容 {}",result);
    }

    @Test
    public void two() throws JsonProcessingException {
        logger.info("---redistemplate测试---");
        User user = new User("1","debug","因陀罗");
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        final String content = objectMapper.writeValueAsString(user);
        final String key = "redis:template:two:object";
        logger.info("写入缓存内存 {}" , content);
        stringObjectValueOperations.set(key,content);
        Object result = stringObjectValueOperations.get(key);
        User readValue = objectMapper.readValue(result.toString(), User.class);
        logger.info("读出内容 {}",readValue);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void three(){
        logger.info("---StringRedisTemplate测试---");
        final String content = " StringRedisTemplate字符串信息";
        final String key = "redis:template:three:string";
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        logger.info("写入缓存内存 {}" ,content);
        opsForValue.set(key,content);
        String result = opsForValue.get(key);
        logger.info("读出内容 {}",result);
    }

    @Test
    public void four() throws JsonProcessingException {
        logger.info("---StringRedisTemplate测试---");
        User user = new User("1","debug","因陀罗");
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        final String content = objectMapper.writeValueAsString(user);
        final String key = "redis:template:four:object";
        logger.info("写入缓存内存 {}" , content);
        opsForValue.set(key,content);
        String result = opsForValue.get(key);
        User readValue = objectMapper.readValue(result, User.class);
        logger.info("读出内容 {}",readValue);
    }

    @Test
    public void list(){
        logger.info("---List测试---");
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("1","张三","242@qq.com"));
        users.add(new User("2","李四","242@qq.com"));
        users.add(new User("3","王二","242@qq.com"));
        logger.info("list队列{}",users);
        final String key = "redis:list:1";
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        for(User user : users){
            opsForList.leftPush(key,user);
        }
        Object index = opsForList.index(key, 1);
        logger.info("读取用户{}",index);
        //        Object readValue = opsForList.rightPop(key);
//        User user;
//        while(readValue != null){
//            user = (User)readValue;
//            logger.info("当前读取用户{}",user);
//            readValue = opsForList.rightPop(key);
//        }
    }

    @Test
    public void set(){
        List<String> names = new ArrayList<>();
        names.add("张三");
        names.add("张三");
        names.add("李思思");
        names.add("李四");
        names.add("张三");
        names.add("王二");
        names.add("李思思");
        names.add("李四");
        logger.info("集合中的对象{}",names);
        final String key = "redis:set:1";
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        for(String name : names){
            opsForSet.add(key,name);
        }

        Object readValue = opsForSet.pop(key);
        while(readValue != null){
            logger.info("读取的值{}",readValue);
            readValue = opsForSet.pop(key);
        }
    }

    @Test
    public void sortSet(){
        ArrayList<PhoneUser> list = new ArrayList<>();
        list.add(new PhoneUser("101",110));
        list.add(new PhoneUser("102",20));
        list.add(new PhoneUser("103",80));
        list.add(new PhoneUser("104",150));
        list.add(new PhoneUser("105",60));
        list.add(new PhoneUser("101",50));
        list.add(new PhoneUser("103",120));
        list.add(new PhoneUser("105",10));
        logger.info("集合中的数据{}",list);
        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
        String key = "redis:zset:1";
        for(PhoneUser phoneUser : list){
            opsForZSet.add(key,phoneUser,phoneUser.getMoney());
        }
        Set<Object> set = opsForZSet.range(key, 0, -1);
        logger.info("读取的数据{}",set);
        Set<Object> reverseRange = opsForZSet.reverseRange(key, 0, -1);
        logger.info("逆序数据{}",reverseRange);
    }

    @Test
    public void hash(){
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Fruit> fruits = new ArrayList<Fruit>();
        users.add(new User("1","张三","12@qq.com"));
        users.add(new User("2","李四","12@qq.com"));
        users.add(new User("3","王二","12@qq.com"));
        fruits.add(new Fruit("苹果","red"));
        fruits.add(new Fruit("菠萝","yellow"));
        fruits.add(new Fruit("橘子","green"));
        logger.info("用户集合{}",users);
        logger.info("水果集合{}",fruits);
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        String userKey = "redis:hash:1";
        String fruitKey = "redis:hash:2";
        for(User user : users){
            opsForHash.put(userKey,user.getId(),user);
        }
        for(Fruit fruit: fruits){
            opsForHash.put(fruitKey,fruit.getName(),fruit);
        }
        Map<Object, Object> userMap = opsForHash.entries(userKey);
        logger.info("用户{}",userMap);
        Map<Object, Object> fruitMap = opsForHash.entries(fruitKey);
        logger.info("水果{}",fruitMap);
        User u = (User) opsForHash.get(userKey, "1");
        logger.info("用户{}",u);
        Fruit f = (Fruit) opsForHash.get(fruitKey, "苹果");
        logger.info("水果{}",f);
    }

    @Test
    public void listTest(){
        List<Fruit> fruits = new ArrayList<Fruit>();
        fruits.add(new Fruit("苹果","red"));
        fruits.add(new Fruit("菠萝","yellow"));
        fruits.add(new Fruit("橘子","green"));
        redisTemplate.opsForList().leftPushAll("redis:list:4", fruits.toArray());
        logger.info("数据：{}",fruits);
        Object o = redisTemplate.opsForList().rightPop("redis:list:4");
        logger.info("读取数据{}",o);
    }
}
