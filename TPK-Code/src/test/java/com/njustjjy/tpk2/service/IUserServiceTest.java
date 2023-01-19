package com.njustjjy.tpk2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IUserServiceTest {

    @Autowired
    IUserService userService;

    @Test
    public void updateUserPasswordTest(){
        userService.updateUserPassword("LODQ221013360887","123456",null,"开发 -> 晓轩");
    }

    @Test
    public void getUserByRecordNumberTest(){
        System.out.println("user --> " + userService.getUserByRecordNumber("LODQ221013360887"));
    }
}
