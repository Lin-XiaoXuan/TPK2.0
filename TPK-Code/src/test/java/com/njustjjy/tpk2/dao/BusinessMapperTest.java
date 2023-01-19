package com.njustjjy.tpk2.dao;

import com.njustjjy.tpk2.entity.Business;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BusinessMapperTest {
    @Autowired
    BusinessMapper businessMapper;

    @Test
    public void addBusinessTest(){
        Business business = new Business();
//        businessMapper.addBusiness();
    }

    @Test
    public void queryLimitBusinessTest(){
        List<Business> bus = businessMapper.queryLimitBusinessforUser("YDIX220926728907",0,5);
        for(Business business : bus){
            System.out.println(business);
        }
    }

    @Test
    public void queryLimitBusinessByEmpNumberTest(){
        List<Business> bus = businessMapper.queryLimitBusinessByEmpNumber("20221013",0,5);
        for(Business business : bus){
            System.out.println(business);
        }
    }

    @Test
    public void queryCountBusinessByRecordNumber(){
        System.out.println(businessMapper.queryCountBusinessByRecordNumber("YDIX220926728907"));
    }

    @Test
    public void queryCountBusinessByEmpNumberTest(){
        System.out.println(businessMapper.queryCountBusinessByEmpNumber("20221013"));
    }

    @Test
    public void queryBusinessByEmpNumber(){
        System.out.println(businessMapper.queryBusinessByEmpNumber("20221013"));
    }

    @Test
    public void updateIsDeleteByNumberTest(){
        System.out.println(businessMapper.updateIsDeleteByNumber("202209262397114",1));
    }

    @Test
    public void queryBusinessByNumberTest(){
        System.out.println(businessMapper.queryBusinessByNumber("202209262397114"));
    }

    @Test
    public void updateBusinessProgressByNumberTest(){
        System.out.println(businessMapper.updateBusinessProgressByNumber("202209262397114",0,"开发：晓轩",LocalDateTime.now()));
    }

    @Test
    public void updateBusinessByNumberTest(){
        Business bus = new Business();
        bus.setBid(31);
        bus.setNumbers("202209262397114");
//        bus.setType();
        bus.setRemark("加急");
        bus.setMoney(12.00);
        bus.setModifiedTime(LocalDateTime.now());
        bus.setModifiedUser("测试--晓轩");

        System.out.println(businessMapper.updateBusinessByNumber(bus));
    }

    @Test
    public void queryAllBusinessByRecordNumberTest(){
        System.out.print(businessMapper.queryAllBusinessByRecordNumber("WNUI221005287047"));
    }

//    @Test
//    public void queryBusinessAndUserMixDataBybusinessNumber(){
//        System.out.println(businessMapper.queryBusinessAndUserMixDataBybusinessNumber("202211222465726"));
//    }

}
