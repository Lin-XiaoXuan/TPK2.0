package com.njustjjy.tpk2.config;

import com.njustjjy.tpk2.listener.RadioListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.njustjjy.tpk2.utils.RabbitMQConstants.QUEUE_NAME_PREFIX;

/**
 * 用于配置RabbitMQ监听的对象
 * */
@Configuration
public class RabbitMQConsumerConfig {
    @Autowired
    ConnectionFactory connectionFactory;

    @Bean
    public SimpleMessageListenerContainer modbusMessageContainer(RadioListener buinListener){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

        container.addQueueNames(QUEUE_NAME_PREFIX + "CALL");

        container.setExposeListenerChannel(true);
        container.setMessageListener(buinListener); //监听处理类
        return container;
    }

//    @Bean
//    public SimpleMessageListenerContainer modbusMessageContainer2(RadioListener radioListener){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//
//        container.addQueueNames(QUEUE_NAME_PREFIX + "CALL");
//
//        container.setExposeListenerChannel(true);
//        container.setMessageListener(radioListener); //监听处理类
//
//        return container;
//    }
}
