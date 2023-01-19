package com.njustjjy.tpk2.controller;

import com.njustjjy.tpk2.entity.User;
import com.njustjjy.tpk2.service.IUserService;
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
@RequestMapping("user")
@Api(tags = "User对应操作接口")
@Log4j2
public class UserContoller extends BaseContoller{

    @Autowired
    IUserService userService;
    /**
     * 修改密码操作
     *
     *
     * @author  林晓轩
     * @version V2.0 2022-9-29
     * @since   @TPK/v2.0
     * @see
     *
     */
    @PatchMapping("updateUserPassword/{oldPass}/{newPass}/{who}")
    @Operation(summary = "修改登录密码操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oldPass",value="旧的密码(6位)",dataTypeClass = String.class),
            @ApiImplicitParam(name="newPass",value="新的密码(6位)",dataTypeClass = String.class),
            @ApiImplicitParam(name="who",value="是谁修改了这个密码",dataTypeClass = String.class)
    })
    public JsonResult<Void> updateUserPassword(@PathVariable("oldPass") String oldPass,
                                               @PathVariable("newPass") String newPass,
                                               @PathVariable("who") String who,
                                               @ApiIgnore HttpSession session){
        //获取对应的业务号
        String recordNumber = getRecordNumberFromSession(session);

        //修改密码操作
        userService.updateUserPassword(recordNumber,oldPass,newPass,who);

        //返回值
        return new JsonResult<Void>(OK);
    }
    
    //获取名字
    @GetMapping("/getData")
    public JsonResult<User> getData(@ApiIgnore HttpSession session){
        log.info("进入getName接口");
        //获取对应的RecordNumber
        String recordNumber = getRecordNumberFromSession(session);

        //获取对象
        User user = userService.getUserByRecordNumber(recordNumber);

        //将数据存储在user对象
        session.setAttribute("userdata",user);

        //后端获取
       return new JsonResult<User>(OK,user);
    }

}
