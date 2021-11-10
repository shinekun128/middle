package cn.ponyzhang.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.amqp.core.Queue;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfigDeadQueue {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConfigDeadQueue.class);
    //@Autowired注解按照类型注入，如果需要按照名称注入@Qualifier("beanName")
    //@Resource注解,默认按照byName注入，name,type两个属性配置，按照配置要求注入
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private Environment env;

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue basicDeadQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange",env.getProperty("mq.dead.exchange.name"));
        args.put("x-dead-letter-routing-key",env.getProperty("mq.dead.routing.key.name"));
        args.put("x-message-ttl",10000);
        return new Queue(env.getProperty("mq.dead.queue.name"),true,false,false,args);
    }

    /**
     * 真实交换机
     * @return
     */
    @Bean
    public TopicExchange basicProducerExchange(){
        return new TopicExchange(env.getProperty("mq.basic.exchange.name"),true,false);
    }

    /**
     * 生产者绑定
     * @return
     */
    @Bean
    public Binding basicProducerBinding(){
        return BindingBuilder.bind(basicDeadQueue()).to(basicProducerExchange()).with(env.getProperty("mq.basic.routing.key.name"));
    }

    @Bean
    public Queue realConsumerQueue(){
        return new Queue(env.getProperty("mq.real.queue.name"),true);
    }

    @Bean
    public TopicExchange basicDeadExchange(){
        return new TopicExchange(env.getProperty("mq.dead.exchange.name"),true,false);
    }

    /**
     * 死信交换机和真实队列、路由进行绑定
     * @return
     */
    @Bean
    public Binding basicDeadBinding(){
        return BindingBuilder.bind(realConsumerQueue()).to(basicDeadExchange()).with(env.getProperty("mq.dead.routing.key.name"));
    }


    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue orderDeadQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange",env.getProperty("mq.order.dead.exchange.name"));
        args.put("x-dead-letter-routing-key",env.getProperty("mq.order.dead.routing.key.name"));
        args.put("x-message-ttl",10000);
        return new Queue(env.getProperty("mq.order.dead.queue.name"),true,false,false,args);
    }

    /**
     * 真实交换机
     * @return
     */
    @Bean
    public TopicExchange orderProducerExchange(){
        return new TopicExchange(env.getProperty("mq.order.exchange.name"),true,false);
    }

    /**
     * 生产者绑定
     * @return
     */
    @Bean
    public Binding orderProducerBinding(){
        return BindingBuilder.bind(orderDeadQueue()).to(orderProducerExchange()).with(env.getProperty("mq.order.routing.key.name"));
    }

    @Bean
    public Queue orderConsumerQueue(){
        return new Queue(env.getProperty("mq.consumer.order.queue.name"),true);
    }

    @Bean
    public TopicExchange orderDeadExchange(){
        return new TopicExchange(env.getProperty("mq.order.dead.exchange.name"),true,false);
    }

    /**
     * 死信交换机和真实队列、路由进行绑定
     * @return
     */
    @Bean
    public Binding orderDeadBinding(){
        return BindingBuilder.bind(orderConsumerQueue()).to(orderDeadExchange()).with(env.getProperty("mq.order.dead.routing.key.name"));
    }
}
