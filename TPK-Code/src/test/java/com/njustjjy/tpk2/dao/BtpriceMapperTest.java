package com.njustjjy.tpk2.dao;

import com.alibaba.fastjson2.JSON;
import com.njustjjy.tpk2.entity.Btprice;
import com.njustjjy.tpk2.utils.Redis6Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.njustjjy.tpk2.utils.RedisConstants.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BtpriceMapperTest {
    @Autowired
    private BtpriceMapper btpriceMapper;
    @Autowired
    private Redis6Utils redis6Utils;

    @Test
    public void queryAllBusinessTypeTest(){
//       List<Btprice> list = btpriceMapper.queryAllBusinessType();
//       //Map
//       Map<String,String> map = new HashMap<String,String>();
//       String key = "bustypedata";
//       //存入值算法
//       for(Btprice b : list){
//           map.put((b.getPid().toString()),JSON.toJSONString(b.toString()));
//       }
//
//       //存入数据
//       redis6Utils.putAll(key,map,60*50);

       Map<Object,Object> maps = redis6Utils.getHashEntries(PRICE_TABLE);
       System.out.println(maps.isEmpty());

       for (Object keys : maps.keySet()){
            System.out.println(keys);
       }
       //获取所有数据

    }
}
