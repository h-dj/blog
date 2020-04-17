package cn.hdj.hdjblog.utils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 13:20
 * @description: 消息队列测试
 */
public class RabbitmqTest {


    private static RabbitTemplate rabbitTemplate;

    @Before
    public void init(){
        ConnectionFactory factory=new CachingConnectionFactory("127.0.0.1",5672);
        rabbitTemplate=new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

    }

    @Test
    public void test(){
        rabbitTemplate.convertAndSend("");
    }
}
