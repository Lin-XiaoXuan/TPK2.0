package com.njustjjy.tpk2.controller;

import com.njustjjy.tpk2.entity.Record;
import com.njustjjy.tpk2.service.IRecordService;
import com.njustjjy.tpk2.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("record")
@Api(tags = "Record对应操作接口")
@Log4j2
public class RecordContoller extends BaseContoller{

    @Autowired
    IRecordService recordService;

    @GetMapping("/login/{idCard}")
    @Operation(summary = "登录操作接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="idCard",value="客户身份证(位数在18位)",dataTypeClass = String.class)
    })
    public JsonResult<Void> login(
            @PathVariable("idCard") String idCard,
            @ApiIgnore HttpSession session
    ){

        //登录操作
        Record record = recordService.login(idCard);

        //将获取的数据存入session中
        session.setAttribute("rid",record.getRid());
        session.setAttribute("recordNumber",record.getNumber());

        //返回
        return new JsonResult<Void>(OK);
    }


    @PostMapping("/register/{idCard}/{password}/{who}/{recordLevel}/{remark}")
    @Operation(summary = "注册操作接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="idCard",value="客户身份证(18位)",dataTypeClass = String.class),
            @ApiImplicitParam(name="password",value="客户设置的档案查询密码(6位)",dataTypeClass = String.class),
            @ApiImplicitParam(name="who",value="谁使用的这个接口(传入测试者)",dataTypeClass = String.class),
            @ApiImplicitParam(name="recordLevel",value="档案等级1 或者 2 ",dataTypeClass = Integer.class),
            @ApiImplicitParam(name="remark",value="建立该档案时的备注，传入null则表示没有",dataTypeClass = String.class)
    })
    public JsonResult<Void> register(@PathVariable String idCard,
                                     @PathVariable String password,
                                     @PathVariable String who,
                                     @PathVariable Integer recordLevel,
                                     @PathVariable String remark){
        //注册操作
        recordService.register(idCard,password,who,recordLevel,remark);

        //返回
        return new JsonResult<Void>(OK);
    }

    /**
     * 登出操作
     *
     *
     * @author  林晓轩
     * @version V2.0 2022-9-29
     * @since   @TPK/v2.0
     * @see
     *
     */
    @DeleteMapping("logout")
    public Integer logout(HttpSession session){
        //清空session
        session.invalidate();
        return 1;
    }
}
