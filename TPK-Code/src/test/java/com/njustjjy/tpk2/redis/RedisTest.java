package com.njustjjy.tpk2.redis;

import com.njustjjy.tpk2.utils.Redis6Utils;
import com.njustjjy.tpk2.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Redis6Utils redis6Utils;

    @Test
    public void keyTypeOperationsTest(){
        redis6Utils.set("student","student");
        Object str = redis6Utils.get("students");
        System.out.println(str);
    }

    @Test
    public void test(){
        Integer i = 28;
        double b = i.doubleValue();
        System.out.println(b);
    }

    @Test
    public void testS(){
        redisTemplate.delete("business:WNUI221005287047");
    }
}
