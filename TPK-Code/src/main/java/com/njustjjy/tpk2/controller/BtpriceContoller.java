package com.njustjjy.tpk2.controller;

import com.njustjjy.tpk2.entity.Btprice;
import java.util.List;

import com.njustjjy.tpk2.service.IBtpriceService;
import com.njustjjy.tpk2.utils.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("btprice")
@Log4j2
public class BtpriceContoller extends BaseContoller {

    @Autowired
    IBtpriceService btpriceService;

    @GetMapping({"/{parent}",""})
    public JsonResult<List<Btprice>> getBtprice(@PathVariable String parent){
        List<Btprice> btprices = btpriceService.getByParent(parent);
        return new JsonResult<List<Btprice>>(OK,btprices);
    }

    @GetMapping("/getPrice/{codes}")
    public JsonResult<String> getPriceByCodes(@PathVariable String codes){
        //获取数据
        String price = btpriceService.getPriceByCodes(codes);
        return new JsonResult<String>(OK,price);
    }
}
