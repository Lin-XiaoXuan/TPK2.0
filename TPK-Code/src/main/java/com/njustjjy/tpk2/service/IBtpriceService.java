package com.njustjjy.tpk2.service;

import com.njustjjy.tpk2.entity.Btprice;
import java.util.List;

public interface IBtpriceService {
    /**
     * @param parent -- 上级类的codes
     *
     * */
    List<Btprice> getByParent(String parent);

    /**
     * @param codes -- 查询的codes
     *
     * @return*/
    String getPriceByCodes(String codes);
}
