package com.njustjjy.tpk2.controller.manager;

import com.njustjjy.tpk2.controller.BaseContoller;
import com.njustjjy.tpk2.entity.Admin;
import com.njustjjy.tpk2.utils.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminContoller extends BaseContoller {
    @GetMapping("/login/{}")
    public JsonResult<Admin> login(String uname,String password){
        return new JsonResult<>(OK);
    }
}
