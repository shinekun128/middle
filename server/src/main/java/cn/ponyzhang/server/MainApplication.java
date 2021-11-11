package cn.ponyzhang.server;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MapperScan(basePackages = "cn.ponyzhang.server.mapper")
@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

    @Autowired
    private Environment env;

    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(env.getProperty("zk.host")).
                namespace(env.getProperty("zk.namespace")).
                retryPolicy(new RetryNTimes(5, 1000)).build();
        curatorFramework.start();
        return curatorFramework;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
