package com.njustjjy.tpk2.service.impl;

import com.njustjjy.tpk2.dao.BtpriceMapper;
import com.njustjjy.tpk2.entity.Btprice;
import java.util.List;

import com.njustjjy.tpk2.exception.TPKSelectException;
import com.njustjjy.tpk2.service.IBtpriceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BtpriceServiceImpl implements IBtpriceService {
    @Autowired
    BtpriceMapper btpriceMapper;

    @Override
    public List<Btprice> getByParent(String parent) {
        //获得返回值
        List<Btprice> dbResult = btpriceMapper.findByParent(parent);
        //将一些不需要的值设置为空
        for (Btprice btprice : dbResult){
            btprice.setPid(null);
            btprice.setDescribes(null);
            btprice.setCreatedTime(null);
            btprice.setCreatedUser(null);
        }

        //返回值
        return dbResult;
    }

    @Override
    public String getPriceByCodes(String codes) {
        //获取价格
        String price =  btpriceMapper.findPriceByCodes(codes).toString();
        //判断数据是否存在
        if(price == null){
            throw new TPKSelectException("查询数据时发生异常");
        }
        return price;
    }
}
