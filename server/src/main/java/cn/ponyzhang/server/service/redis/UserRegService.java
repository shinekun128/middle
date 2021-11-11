package cn.ponyzhang.server.service.redis;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.UserRegDto;
import cn.ponyzhang.server.entity.UserReg;
import cn.ponyzhang.server.mapper.UserRegMapper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserRegService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegService.class);

    @Autowired
    private UserRegMapper userRegMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private DefaultRedisScript<Long> redisScript;

    public BaseResponse reg(UserRegDto dto) {
        UserReg userReg = userRegMapper.selectByUserName(dto.getUserName());
        BaseResponse response = null;
        if (userReg == null) {
            userReg = new UserReg();
            BeanUtils.copyProperties(dto, userReg);
            userReg.setCreateTime(new Date());
            int res = userRegMapper.insert(userReg);
            if (res > 0) {
                response = new BaseResponse(StatusCode.SUCCESS);
                response.setData("插入数据库成功");
                logger.info("用户注册成功，当前用户名为：{}", dto.getUserName());
            } else {
                response = new BaseResponse(StatusCode.FAIL);
                response.setData("插入数据库失败");
            }
        } else {
            response = new BaseResponse(StatusCode.FAIL);
            response.setData("用户名已存在，请重新输入");
        }
        return response;
    }

    public BaseResponse regByRedis(UserRegDto dto) {
        BaseResponse response = null;
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        final String key = dto.getUserName() + "-lock";
        final String value = System.nanoTime() + "" + UUID.randomUUID();
        Boolean res = opsForValue.setIfAbsent(key, value, 20L, TimeUnit.SECONDS);
        if (res) {
            try {
                UserReg userReg = userRegMapper.selectByUserName(dto.getUserName());
                if (userReg == null) {
                    userReg = new UserReg();
                    BeanUtils.copyProperties(dto, userReg);
                    userReg.setCreateTime(new Date());
                    int insert = userRegMapper.insert(userReg);
                    if (insert > 0) {
                        response = new BaseResponse(StatusCode.SUCCESS);
                        response.setData("插入数据库成功");
                        logger.info("加入分布式锁，用户注册成功，当前用户名为：{}", dto.getUserName());
                    } else {
                        response = new BaseResponse(StatusCode.FAIL);
                        response.setData("插入数据库失败");
                    }
                }
            } catch (BeansException e) {
                e.printStackTrace();
            } finally {
                long result = redisTemplate.execute(redisScript, Arrays.asList(key), value);
                if (result != 1L) {
                    response = new BaseResponse(StatusCode.FAIL);
                    response.setData("未能成功删除key");
                }
            }
        } else {
            response = new BaseResponse(StatusCode.FAIL);
            response.setData("获取分布式锁失败，用户名已存在");
        }
        return response;
    }

    @Autowired
    private CuratorFramework curatorFramework;

    public BaseResponse userRegWithZkLock(UserRegDto dto) {
        BaseResponse response = null;
        final String keyPrefix = "/middle/zkLock/";
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, keyPrefix + dto.getUserName() + "-lock");
        try {
            if (mutex.acquire(10L, TimeUnit.SECONDS)) {
                UserReg userReg = userRegMapper.selectByUserName(dto.getUserName());
                if (userReg == null) {
                    userReg = new UserReg();
                    BeanUtils.copyProperties(dto, userReg);
                    userReg.setCreateTime(new Date());
                    int insert = userRegMapper.insert(userReg);
                    if (insert > 0) {
                        response = new BaseResponse(StatusCode.SUCCESS);
                        response.setData("插入数据库成功");
                        logger.info("加入zk分布式锁，用户注册成功，当前用户名为：{}", dto.getUserName());
                    } else {
                        response = new BaseResponse(StatusCode.FAIL);
                        response.setData("插入数据库失败");
                    }
                } else {
                    response = new BaseResponse(StatusCode.FAIL);
                    response.setData("用户信息已存在");
                }
            } else {
                response = new BaseResponse(StatusCode.FAIL);
                response.setData("后去zk分布式锁失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mutex.release();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return response;
    }

    @Autowired
    private RedissonClient redissonClient;

    public BaseResponse userRegRedisson(UserRegDto dto) {
        BaseResponse response = null;
        final String keyPrefix = "redissonOneLock-" + dto.getUserName();
        RLock lock = redissonClient.getLock(keyPrefix);
        try {
            lock.lock(10l, TimeUnit.SECONDS);
            UserReg userReg = userRegMapper.selectByUserName(dto.getUserName());
            if (userReg == null) {
                userReg = new UserReg();
                BeanUtils.copyProperties(dto, userReg);
                userReg.setCreateTime(new Date());
                int insert = userRegMapper.insert(userReg);
                if (insert > 0) {
                    response = new BaseResponse(StatusCode.SUCCESS);
                    response.setData("插入数据库成功");
                    logger.info("加入zk分布式锁，用户注册成功，当前用户名为：{}", dto.getUserName());
                } else {
                    response = new BaseResponse(StatusCode.FAIL);
                    response.setData("插入数据库失败");
                }
            } else {
                response = new BaseResponse(StatusCode.FAIL);
                response.setData("用户信息已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock.isLocked())
                lock.unlock();
        }
        return response;
    }
}