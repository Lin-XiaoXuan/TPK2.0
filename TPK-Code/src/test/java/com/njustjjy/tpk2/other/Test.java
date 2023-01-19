package com.njustjjy.tpk2.other;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


public class Test {

    /**
     * 文字转换语音
     *
     * */
    public static void test(String text){

    }



    public static void main(String[] args) {
        Thread p1 = new Thread(new People01());
        Thread p2 = new Thread(new People02());

        p1.setName("1号业务");
        p2.setName("2号业务");

        //启动线程
        p1.start();
        p2.start();
    }
}

class People01 implements Runnable{
    @Override
    public void run() {
        //获取对象
        JacobUtils jacobUtils = JacobUtils.getInstance();
        //调用呼号方法

        for(int i = 0; i < 100;i++){
            System.out.println(Thread.currentThread().getName() + "调用");
            jacobUtils.sing( i + "号 林 晓轩请到4号窗口");
        }


    }
}

class People02 implements Runnable{
    @Override
    public void run() {
        //获取对象
        JacobUtils jacobUtils =  JacobUtils.getInstance();
        //调用呼号方法
        for(int i = 0; i < 100;i++){
            System.out.println(Thread.currentThread().getName() + "调用");
            jacobUtils.sing(i + "号 林 正峰请到4号窗口");
        }

    }
}


