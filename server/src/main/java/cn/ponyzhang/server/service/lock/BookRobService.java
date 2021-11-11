package cn.ponyzhang.server.service.lock;

import cn.ponyzhang.api.dto.BaseResponse;
import cn.ponyzhang.api.enums.StatusCode;
import cn.ponyzhang.server.dto.BookRobDto;
import cn.ponyzhang.server.entity.BookRob;
import cn.ponyzhang.server.entity.BookStock;
import cn.ponyzhang.server.mapper.BookRobMapper;
import cn.ponyzhang.server.mapper.BookStockMapper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class BookRobService {
    private static final Logger logger = LoggerFactory.getLogger(BookRobService.class);

    @Autowired
    private BookRobMapper bookRobMapper;

    @Autowired
    private BookStockMapper bookStockMapper;

    public BaseResponse bookRob(BookRobDto dto){
        BaseResponse response = rob(dto);
        return response;
    }

    @Autowired
    private CuratorFramework curatorFramework;

    public BaseResponse bookRobWithLock(BookRobDto dto){
        BaseResponse response = null;
        final String keyPrefix = "/middle/zkLock/";
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, keyPrefix + dto.getBookNo() + dto.getUserId() + "-lock");
        try {
            if(mutex.acquire(15L, TimeUnit.SECONDS)){
                response = rob(dto);
            }else{
                response = new BaseResponse(StatusCode.FAIL);
                response.setData("获取zk分布式锁失败");
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

    public BaseResponse rob(BookRobDto dto){
        BaseResponse response = null;
        BookRob bookRob = bookRobMapper.selectByUserIdAndBookNo(dto.getUserId(), dto.getBookNo());
        if(bookRob == null){
            BookStock bookStock = bookStockMapper.selectByBookNo(dto.getBookNo());
            if(bookStock != null && bookStock.getStock() > 0){
                //可以抢购
                int res = bookStockMapper.updateStock(dto.getBookNo());
                if(res > 0){
                    bookRob = new BookRob();
                    bookRob.setBookNo(dto.getBookNo());
                    bookRob.setRobTime(new Date());
                    bookRob.setUserId(dto.getUserId());
                    bookRobMapper.insert(bookRob);
                    response = new BaseResponse(StatusCode.SUCCESS);
                    response.setData("抢购成功，请尽快付款");
                    logger.info("抢购成功，用户id:{}",dto.getUserId());
                }
            }else{
                response = new BaseResponse(StatusCode.FAIL);
                response.setData("书籍不存在或者库存不足");
            }
        }else{
            response = new BaseResponse(StatusCode.FAIL);
            response.setData("不可以重复抢购");
        }
        return response;
    }
}
