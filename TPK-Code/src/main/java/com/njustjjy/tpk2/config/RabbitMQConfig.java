package com.njustjjy.tpk2.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.njustjjy.tpk2.utils.RabbitMQConstants.*;


/**
 * 该类配置一些RabbitMQ的链接和公共的一些使用配件
 *  --- ConnectionFactory、RabbitAdmin、RabbitTemplate
 * */
@Configuration
@Log4j2
public class RabbitMQConfig {

    //创建工厂链接类
    @Bean
    public ConnectionFactory connectionFactory() {
        //创建链接
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("139.159.250.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("xiaoxuan");
        connectionFactory.setPassword("lin6280178");
        connectionFactory.setVirtualHost("/TPK");
        connectionFactory.setPublisherReturns(true);

        return connectionFactory;
    }

    //创建所有交换机
    @Bean
    public RabbitAdmin rabbitAdmin(){
        return new RabbitAdmin(connectionFactory());
    }
    /**
     * 1、key ： 队列名
     * 2、value ： 队列的匹配规则
     *
     * */
    private static Map<String,String> queueList = new HashMap<String,String>();

    /**
     * 添加初始化队列
     *
     * */
    @Bean
    public void addQueue(){
        //获取RabbitAdmin对象
        RabbitAdmin rabbitAdmin = rabbitAdmin();

        //创建交换机
        DirectExchange exchange = new DirectExchange(EXCHANG_TPK);
        rabbitAdmin.declareExchange(exchange);

        //申请业务时的存储队列
        queueList.put(QUEUE_NAME_PREFIX + "BUIN",QUEUE_BUIN_IDENTIFY);
        //呼号队列，当数据进入这个队列时呼号业务获取该数据然后呼号
        queueList.put(QUEUE_NAME_PREFIX + "CALL",QUEUE_CALL_IDENTIFY);

        //将队列和交换机绑定
        Queue queue = null;
        for (String queueName : queueList.keySet()){
            //获取对应的规则
            String queueRule = queueList.get(queueName);

            //将数据封装为一个Queue的对象，并创建
            queue = new Queue(queueName,true);
            rabbitAdmin.declareQueue(queue);

            //绑定操作
            Binding bing = BindingBuilder.bind(queue).to(exchange).with(queueRule);
            try {
                rabbitAdmin.declareBinding(bing);
            } catch (Exception e) {
                log.error("RabbitMQ初始化绑定操作错误");
                e.printStackTrace();
            }
        }
    }

    //装配RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }
}
