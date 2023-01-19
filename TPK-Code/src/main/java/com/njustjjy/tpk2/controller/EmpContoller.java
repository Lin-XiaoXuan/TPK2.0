package com.njustjjy.tpk2.controller;

import com.njustjjy.tpk2.entity.Emp;
import com.njustjjy.tpk2.service.IEmpService;
import com.njustjjy.tpk2.utils.JsonResult;
import com.njustjjy.tpk2.utils.Redis6Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

import static com.njustjjy.tpk2.utils.RedisConstants.EMP_ROUND_QUEUE;


@RestController
@RequestMapping("emp")
@Api(tags = "emp对应操作接口")
@Log4j2
public class EmpContoller extends BaseContoller{
    @Autowired
    IEmpService empService;
    @Autowired
    Redis6Utils redis6Utils;

    @GetMapping("/login/{empNumber}/{winNumber}")
    @Operation(summary = "登录操作接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="empNumber",value="员工工号(8位)",dataTypeClass = String.class),
            @ApiImplicitParam(name="empNumber",value="当前业务员所在窗口",dataTypeClass = String.class)
    })
    public JsonResult<Void> login(@PathVariable("empNumber") String empNumber,
                                  @PathVariable("winNumber") Integer winNumber,
                                  @ApiIgnore HttpSession session){
        //后端获取数据
        Emp emp = empService.login(empNumber,winNumber);
        //存入数据
        String empNumber2 = emp.getNumber();
        session.setAttribute("eid",emp.getEid());
        session.setAttribute("empNumber",empNumber2);
        session.setAttribute(empNumber,winNumber);

        //存入在线循环队列中
        redis6Utils.leftPush(EMP_ROUND_QUEUE,empNumber2 + "-" +winNumber);

        return new JsonResult<Void>(OK);
    }
}
