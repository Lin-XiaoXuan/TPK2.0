package com.njustjjy.tpk2.service;

import com.njustjjy.tpk2.dao.BtpriceMapper;
import com.njustjjy.tpk2.entity.Btprice;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IBtpriceServiceTest {
    @Autowired
    private BtpriceMapper btpriceMapper;

    @Test
    public void findByParentTest(){
        List<Btprice> list = btpriceMapper.findByParent("00000");
        for(Btprice btprice : list){
            System.out.println("btprice = " + btprice);
        }
    }

    @Test
    public void findPriceByCodesTest(){
        Double d = btpriceMapper.findPriceByCodes("11000");
    }
}
