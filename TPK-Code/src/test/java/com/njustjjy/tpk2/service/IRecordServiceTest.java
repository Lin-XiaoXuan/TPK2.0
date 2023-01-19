package com.njustjjy.tpk2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IRecordServiceTest {
    @Autowired
    IRecordService recordService;

    @Test
    public void registerTest(){
        recordService.register("340823200310135617","123456","开发 -> 晓轩",1,null);
    }

    @Test
    public void loginTest(){
        System.out.println("recordService.login() = " + recordService.login("340823200310135617"));
    }
}
