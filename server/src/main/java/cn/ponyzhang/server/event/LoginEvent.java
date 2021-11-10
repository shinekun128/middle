package cn.ponyzhang.server.event;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

@ToString
public class LoginEvent extends ApplicationEvent implements Serializable {
    private String username;
    private String loginTime;
    private String ip;

    public LoginEvent(Object source) {
        super(source);
    }

    public LoginEvent(Object source,String username,String loginTime,String ip){
        super(source);
        this.username = username;
        this.loginTime = loginTime;
        this.ip = ip;
    }
}
