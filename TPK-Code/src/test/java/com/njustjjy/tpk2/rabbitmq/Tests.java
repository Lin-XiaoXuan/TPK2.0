package com.njustjjy.tpk2.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.njustjjy.tpk2.utils.RabbitMQConstants.EXCHANG_TPK;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Tests {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test(){
        rabbitTemplate.convertAndSend(EXCHANG_TPK,"buin","你好");
//        rabbitTemplate.convertAndSend(EXCHANG_TPK,"call","晓轩你好");
    }
}
