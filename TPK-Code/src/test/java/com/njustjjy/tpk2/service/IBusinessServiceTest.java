package com.njustjjy.tpk2.service;

import com.njustjjy.tpk2.dao.BusinessMapper;
import com.njustjjy.tpk2.entity.Business;
import java.util.List;

import com.njustjjy.tpk2.entity.other.BusinessTable;
import com.njustjjy.tpk2.utils.PageBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IBusinessServiceTest {
    @Autowired
    IBusinessService businessService;

    @Test
    public void userAddBusiness(){
        businessService.userAddBusiness("LODQ221013360887","11000","18921356812","你好",10.21,1,"开发 -> 林正峰");
    }

    @Test
    public void addBusinessTest(){
        String recordNumber = "340823200310135617";
        Integer type = 1;
        Integer way = 1;
        String remark = "加急";
        String name = "开发 -> 晓轩";
        businessService.addBusiness(recordNumber,type,way,remark,name,2);
    }

    @Test
    public void queryLimitBusiness(){
        String recordNumber = "LODQ221013360887";
        PageBean<Business> bus = businessService.queryLimitBusiness(recordNumber,1,1,30);
        for(Business business : bus.getResults()){
            System.out.println("business = " + business);
        }
    }

    @Test
    public void repealBusiness(){
        businessService.repealBusiness("202210132904110","开发 -> 晓轩");
    }

    @Test
    public void updateBusinessByNumber(){
        Business business = new Business();
        business.setRemark("测试数据");
        business.setNumbers("202210132904110");
        businessService.updateBusinessByNumber(business,"开发--晓轩");
    }

    @Test
    public void queryLimitBusinessByRecordNumberforUserTest(){
       PageBean result =  businessService.queryLimitBusinessByRecordNumberforUser("YOMA221103601177",1,3);
       System.out.println(result.getResults());
    }

    @Test
    public void queryLimitBusinessByEmpNumberTest(){
        PageBean result = businessService.queryLimitBusinessByEmpNumber("20221013",1,3);
        System.out.println("result = " + result);
    }

    @Test
    public void queryBusinessTableByBusinessNumber(){
        BusinessTable businessTable = businessService.queryBusinessTableByBusinessNumber("202211222465726");
        System.out.println(businessTable);
    }
}
