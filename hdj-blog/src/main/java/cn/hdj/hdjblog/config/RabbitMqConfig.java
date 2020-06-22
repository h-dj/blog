package cn.hdj.hdjblog.config;

import cn.hdj.hdjblog.constaint.RabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 13:13
 * @description: 消息队列配置
 */
@Configuration
public class RabbitMqConfig implements RabbitListenerConfigurer {


    @Bean
    public Queue reIndexQueue() {
        return QueueBuilder
                .durable(RabbitMqConstants.REFRESH_ES_INDEX_QUEUE)
                .build();
    }

    @Bean
    public Exchange reIndexExchange() {
        return ExchangeBuilder.directExchange(RabbitMqConstants.REFRESH_ES_INDEX_QUEUE)
                .durable(true)
                .build();
    }

    @Bean
    public Binding reIndexBinding(Queue reIndexQueue, DirectExchange reIndexExchange) {
        return BindingBuilder.bind(reIndexQueue)
                .to(reIndexExchange)
                .with(RabbitMqConstants.REFRESH_ES_INDEX_QUEUE);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin(RabbitTemplate rabbitTemplate) {
        return new RabbitAdmin(rabbitTemplate);
    }

    /**
     * 配置消息消费处理工厂
     *
     * @param registrar
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    /**
     * 消息处理工厂
     *
     * @return
     */
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(new MappingJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }
}
