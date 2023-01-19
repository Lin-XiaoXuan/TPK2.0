package com.njustjjy.tpk2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmpServiceTest {
    @Autowired
    IEmpService empService;

    @Test
    public void loginTest(){
        System.out.println("empService.login() = " + empService.login("20221013",1));
    }
}
