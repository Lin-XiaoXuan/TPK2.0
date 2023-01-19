package com.njustjjy.tpk2.controller;

import com.njustjjy.tpk2.exception.*;
import com.njustjjy.tpk2.utils.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.PropertyAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpSession;

/**
 * 控制器基类
 * ---系统规定
 * 无法处理错误：1000+
 * 基本用户错误代码：2000+
 * IO传输错误代码：3000+
 *
 * */
@Log4j2
public class BaseContoller {
    //成功访问时的状态码200
    public static final int OK = 200;

    @ExceptionHandler({TPKRuntimeException.class,PropertyAccessException.class})
    public JsonResult<Void> handleException(Throwable e){
        //创建一个JsonResult对象，将接收到的异常传递到result中
        JsonResult<Void> result = new JsonResult<>(e);

        //判断异常类型
        if (e instanceof UserNotFindException){
            result.setState(2001);
            result.setMessage("未找到对应用户数据");
        }
        else if(e instanceof WrongPasswordException){
            result.setState(2002);
            result.setMessage("密码错误异常");
        }
        else if(e instanceof IdcardAstrictException){
            result.setState(2003);
            result.setMessage("身份证限制");
        }
        else if(e instanceof IdcardHaveRecordException){
            result.setState(2004);
            result.setMessage("身份证已经注册");
        }
        else if(e instanceof IdcardNotFindException){
            result.setState(2005);
            result.setMessage("身份证未查询到，请核对");
        }
        else if(e instanceof IdcardNotRegisterException){
            result.setState(2006);
            result.setMessage("身份未注册，请先注册");
        }
        else if(e instanceof RecordNotFindException){
            result.setState(2007);
            result.setMessage("未查询到对应档案");
        }
        else if(e instanceof BusinessNotFindException){
            result.setState(2008);
            result.setMessage("业务未查询到异常");
        }
        else if(e instanceof UserBindingPhoneException){
            result.setState(2009);
            result.setMessage("用户已经绑定了手机号");
        }
        else if(e instanceof BusinessisRepealException){
            result.setState(2010);
            result.setMessage("该业务已经撤销");
        }
        else if (e instanceof TPKHttpConnectException){
            result.setState(4001);
            result.setMessage("网络异常或服务器发生异常");
        }
        else if(e instanceof TPKInsertException){
            result.setState(4002);
            result.setMessage("插入数据时发生未知异常");
        }
        else if(e instanceof TPKUpdateException){
            result.setState(4003);
            result.setMessage("更改数据时发生未知异常");
        }
        else if(e instanceof BtpriceTableException){
            result.setState(4004);
            result.setMessage("初始化错误");
        }
        else if(e instanceof MethodArgumentTypeMismatchException){
            result.setState(5001);
            result.setMessage("不合法的数据");
        }
        else if(e instanceof LoginExpiredException){
            result.setState(5002);
            result.setMessage("登录过期，请重新登录");
        }

        return result;
    }

    //获取rid
    protected final Integer getRidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("rid").toString());
    }

    //获取recordNumber
    protected final String getRecordNumberFromSession(HttpSession session){
        return session.getAttribute("recordNumber").toString();
    }

    protected final String getEmpNumberFromSession(HttpSession session){
        return session.getAttribute("empNumber").toString();
    }

    protected final Integer getEmpWindowsNumber(HttpSession session){
        return (Integer) session.getAttribute("windowNumber");
    }
}
