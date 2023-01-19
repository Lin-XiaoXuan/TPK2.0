package com.njustjjy.tpk2.listener;

import com.njustjjy.tpk2.utils.JacobUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * 广播监听类
 *
 * */
@Component
@Log4j2
public class RadioListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("你好");
        //这里直接获取字符串——格式为：XX号 XXX 请到 NXX号窗口
       String text = new String(message.getBody());
       //获取呼号对象并调用呼号
       JacobUtils jacob = JacobUtils.getInstance();
       jacob.sing(text);
    }
}
