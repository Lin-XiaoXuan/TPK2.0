package com.njustjjy.tpk2.dao;

import com.njustjjy.tpk2.entity.Btprice;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BtpriceMapper {
    //查询所有业务
    List<Btprice> queryAllBusinessType();

    //根据parent查询对应数据
    List<Btprice> findByParent(String parent);

    //根据codes查询价格
    Double findPriceByCodes(String codes);

    //使用业务codes查询
    Btprice queryBtpriceByCodes(String codes);
}
