package com.njustjjy.tpk2.controller;

import com.njustjjy.tpk2.entity.Business;
import com.njustjjy.tpk2.entity.other.BusinessTable;
import com.njustjjy.tpk2.service.IBusinessService;
import com.njustjjy.tpk2.service.IRecordService;
import com.njustjjy.tpk2.service.IUserService;
import com.njustjjy.tpk2.utils.JacobUtils;
import com.njustjjy.tpk2.utils.JsonResult;
import com.njustjjy.tpk2.utils.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static com.njustjjy.tpk2.utils.RabbitMQConstants.EXCHANG_TPK;
import static com.njustjjy.tpk2.utils.RabbitMQConstants.QUEUE_CALL_IDENTIFY;


@RestController
@RequestMapping("business")
@Api(tags = "business对应操作接口")
@Log4j2
public class BusinessContoller extends BaseContoller{
    @Autowired
    IBusinessService businessService;
    @Autowired
    IUserService userService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 业务发起功能接口
     * 该接口用于生成一条Business信息
     *
     * @author  林晓轩
     * @version V2.0 2022-10-14
     * @since   @TPK/v2.0
     * @see
     *
     * @param businessType --- 业务类型
     * @param way --- 发起途径
     * @param way --- 谁发起了这条信息
     * @param remark --- 业务备注
     */
    //需要修改
//    @PostMapping("/buni/{idCard}/{businessType}/{way}/{who}/{remark}")
//    @Operation(summary = "业务发起功能接口")
//    @Deprecated
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="idCard",value="申请人身份证号，如果传入null则视为客户端情况自动获取recordNumber",dataTypeClass = Integer.class),
//            @ApiImplicitParam(name="businessType",value="业务类型(只能为 1~3)",dataTypeClass = Integer.class),
//            @ApiImplicitParam(name="way",value="用户发起途径 --(只能为 1 和 2)",dataTypeClass = Integer.class),
//            @ApiImplicitParam(name="who",value="谁添加了这条信息",dataTypeClass = String.class),
//            @ApiImplicitParam(name="remark",value="业务描述(最多225个字符)",dataTypeClass = String.class)
//    })
//    public JsonResult<Void> buni(@PathVariable("idCard") String idCard,
//                                 @PathVariable("businessType") Integer businessType,
//                                 @PathVariable("way") Integer way,
//                                 @PathVariable("who") String who,
//                                 @PathVariable("remark") String remark,
//                                 @ApiIgnore HttpSession session){
//        //这个端口通过idCard的传入值来判断是情况1还是情况2，没有使用condition_1这个标识
//        //用户端idCard默认传入null
//        //业务员端口idCard默认传入18位的数据
//
//        //这的condition是用来向Service层标识的
//        Integer condition = 2;
//        String evidence = null;
//
//        //evidence为null则获取session中的recordNumber进行查询
//        if (idCard.equals("null") || idCard == null){
//            evidence = getRecordNumberFromSession(session);
//            condition = 1;
//        }else{
//            evidence = idCard;
//        }
//
//        //如果remark传入值则为null
//        if (remark.length() == 0 || remark.equals("null")){
//            remark = null;
//        }
//
//        //申请业务
//        businessService.addBusiness(evidence,businessType,way,remark,who,condition);
//
//        //返回值
//        return new JsonResult<Void>(OK);
//    }


    /**
     * <p>用户业务发起接口</p>
     *
     * @author  林晓轩
     * @version V2.0 2022-10-14
     * @since   @TPK/v2.0
     * @see
     *
     * */
    //需要修改
    @PostMapping("/userBuni/{businessType}/{remark}/{money}/{phone}/{way}/{who}")
    @Operation(summary = "用户业务发起功能接口")
    @ApiImplicitParams({

    })
    public JsonResult<Void> userBuni(@PathVariable String businessType,
                                     @PathVariable String remark,
                                     @PathVariable Double money,
                                     @PathVariable String phone,
                                     @PathVariable Integer way,
                                     @PathVariable String who,
                                     @ApiIgnore HttpSession session){

        //获取businessNumber
        String recordNumber = getRecordNumberFromSession(session);

        //后端请求接口
        businessService.userAddBusiness(recordNumber,businessType,phone,remark,money,way,who);

        return new JsonResult<>(OK);
    }


    /**
     * 分页查询业务接口
     *
     * @author  林晓轩
     * @version V2.0 2022-9-26
     * @since   @TPK/v2.0
     * @see
     *
     * @param currentPage --- Integer 当前页
     * @param rows --- Integer 每页条数
     */
    @GetMapping("queryLimitBusinessByNumberforUser/{currentPage}/{rows}")
    @Operation(summary = "分页查询业务接")
    @ApiImplicitParams({
            @ApiImplicitParam(name="condition_1",value="业务形式 -- 1 查询未完成的业务数据 、2 查询已经完成的业务数据",dataTypeClass = Integer.class),
            @ApiImplicitParam(name="currentPage",value="当前页",dataTypeClass = Integer.class),
            @ApiImplicitParam(name="rows",value="每页条数",dataTypeClass = Integer.class)
    })
    public JsonResult<PageBean> queryLimitBusinessByNumberforUser(
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("rows") Integer rows,
            @ApiIgnore HttpSession session){

        //判断数据
        if(currentPage < 1){
            currentPage = 1;
        }

        //获取recordNumber
//        String recordNumber = getRecordNumberFromSession(session);
        String recordNumber = "YOMA221103601177";
        PageBean data = businessService.queryLimitBusinessByRecordNumberforUser(recordNumber,currentPage,rows);

        return new JsonResult<PageBean>(OK,data);
    }


//    @GetMapping("/queryLimitBusinessByEmpNumber/")
//    @Operation(summary = "业务员分页查询自己负责的业务")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="currentPage",value="当前页数",dataTypeClass = Integer.class),
//            @ApiImplicitParam(name="rows",value="每页条数",dataTypeClass = Integer.class),
//    })
//    public JsonResult<Business> queryLimitBusinessByEmpNumber(
//            @PathVariable("currentPage") Integer currentPage,
//            @PathVariable("rows") Integer rows,
//            HttpSession session){
//
//        //从Session查询出当前的业务员的empNumebr
//        String empNumber = getEmpNumberFromSession(session);
//        //从Service层获取
//
//        return new JsonResult<>(OK);
//    }

    @GetMapping("/queryLimitAllBusinessByEmpNumber/")
    public JsonResult<List<Business>> queryLimitAllBusinessByEmpNumber(HttpSession session){
        //从Session查询出当前的业务员的empNumebr
        String empNumber = getEmpNumberFromSession(session);

        //获取数据
        List<Business> data = businessService.queryBusinessByEmpNumber(empNumber);

        return new JsonResult<List<Business>>(OK,data);
    }

    /**
     * 撤销业务接口
     *
     *
     * @author  林晓轩
     * @version V2.0 2022-9-26
     * @since   @TPK/v2.0
     * @see
     *
     * @param businessNumber --- String Business的number
     */
    @PatchMapping("repealBusiness/{businessNumber}/{who}")
    @Operation(summary = "撤销业务接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="businessNumber",value="业务号(15位)",dataTypeClass = String.class),
            @ApiImplicitParam(name="who",value="是谁撤销了这个业务",dataTypeClass = String.class)
    })
    public JsonResult<Void> repealBusiness(
            @PathVariable("businessNumber") String businessNumber,
            @PathVariable("who") String who,
            @ApiIgnore HttpSession session){

        //执行撤销业务操作
        businessService.repealBusiness(businessNumber,who);
        return new JsonResult<Void>(OK);
    }


    /**
     * 修改business进度，让Busienss进入下一个进度
     *  -- 这个接口单独写，我们需要在这个接口调用呼号机器进行呼号
     * */
    @GetMapping("updateBusinessProgree/{businessNumber}/{businessProgress}/{who}")
    @Operation(summary = "修改业务进度接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="businessNumber",value="要修改的业务号(15位)",dataTypeClass = String.class),
            @ApiImplicitParam(name="businessProgress",value="修改成什么类型业务--- 类型包括 1--2--3",dataTypeClass = Integer.class),
            @ApiImplicitParam(name="who",value="是谁修改了这条信息，如果没有要写的就填null",dataTypeClass = String.class),
    })
    public JsonResult<Void> updateBusinessProgree(
            @PathVariable("businessNumber") String businessNumber,
            @PathVariable("businessProgress") Integer businessProgress,
            @PathVariable("who") String who){

        //后端写入信息
        businessService.updateBusinessProgree(businessNumber,businessProgress,who);

        return new JsonResult<Void>(OK);
    }

    /**
     *
     *
     * */
    @GetMapping("callSign/{businessNumber}/{recordNumber}")
    public JsonResult<Void> callSign(
            @PathVariable String businessNumber,
            @PathVariable String recordNumber,
            HttpSession session){


        //获取empNumber
        String empNumber = getEmpNumberFromSession(session);
        //获得窗口信息
        String window = session.getAttribute(empNumber).toString();
        //获取Name
        String name = userService.getUserByRecordNumber(recordNumber).getName();
        //获取Number号
        String number = businessNumber.substring(11,15);
        //拼接播报字符串
        String songText = "请" + number + "号" + name + "到" + window + "号窗口";
        rabbitTemplate.convertAndSend(EXCHANG_TPK,QUEUE_CALL_IDENTIFY,songText);
//        JacobUtils jacob = JacobUtils.getInstance();
//        jacob.sing(songText);

        return new JsonResult<Void>(OK);
    }

    /**
     *
     *
     * */
    @GetMapping("completionBusinessTable/{businessNumber}")
    public JsonResult<BusinessTable> completionBusinessTable(@PathVariable String businessNumber){
       //后端获取数据
       BusinessTable data = businessService.queryBusinessTableByBusinessNumber(businessNumber);
       return new JsonResult<BusinessTable>(OK,data);
    }
}
