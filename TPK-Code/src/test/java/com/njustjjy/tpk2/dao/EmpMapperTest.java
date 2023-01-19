package com.njustjjy.tpk2.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmpMapperTest {

    @Autowired
    EmpMapper empMapper;

    @Test
    public void queryEmpByNumber(){
        System.out.println(empMapper.queryEmpByEmpNumber("20221012"));
    }
}
