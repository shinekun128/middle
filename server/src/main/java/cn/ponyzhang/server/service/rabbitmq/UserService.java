package cn.ponyzhang.server.service.rabbitmq;

import cn.ponyzhang.server.dto.UserLoginDto;
import cn.ponyzhang.server.entity.User;
import cn.ponyzhang.server.mapper.UserMapper;
import cn.ponyzhang.server.rabbitmq.producer.LogPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogPublisher logPublisher;

    public boolean login(UserLoginDto userLoginDto) throws Exception{
        User user = userMapper.selectByUserNameAndPassword(userLoginDto.getUserName(), userLoginDto.getPassword());
        if(user != null){
            userLoginDto.setId(user.getId());
            logPublisher.sendLogMsg(userLoginDto);
            return true;
        }
        return false;
    }
}
