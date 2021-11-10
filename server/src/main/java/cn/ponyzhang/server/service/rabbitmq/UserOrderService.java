package cn.ponyzhang.server.service.rabbitmq;

import cn.ponyzhang.server.dto.UserOrderDto;
import cn.ponyzhang.server.entity.MqOrder;
import cn.ponyzhang.server.entity.UserOrder;
import cn.ponyzhang.server.mapper.MqOrderMapper;
import cn.ponyzhang.server.mapper.UserOrderMapper;
import cn.ponyzhang.server.rabbitmq.producer.DeadOrderPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserOrderService {
    private static final Logger logger = LoggerFactory.getLogger(UserOrderService.class);

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private MqOrderMapper mqOrderMapper;

    @Autowired
    private DeadOrderPublisher deadOrderPublisher;

    public void pushUserOrderRecord(UserOrderDto userOrderDto){
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(userOrderDto,userOrder);
        userOrder.setStatus(1);
        userOrder.setCreateTime(new Date());
        userOrder.setIsActive(1);
        userOrderMapper.insert(userOrder);
        logger.info("用户成功下单，下单信息为{}",userOrder);
        deadOrderPublisher.sendMsg(userOrder.getId());
    }

    public void updateUserOrderRecord(UserOrder userOrder){
        if(userOrder != null){
            userOrder.setIsActive(0);
            userOrder.setUpdateTime(new Date());
            userOrderMapper.updateByPrimaryKey(userOrder);
            MqOrder mqOrder = new MqOrder();
            mqOrder.setBusinessTime(new Date());
            mqOrder.setMemo("更新失效当前用户下单记录id,orderId="+userOrder.getId());
            mqOrder.setOrderId(userOrder.getId());
            mqOrderMapper.insert(mqOrder);
        }

    }
}
