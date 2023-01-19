package com.njustjjy.tpk2.handler;

import com.alibaba.fastjson2.JSONObject;
import com.njustjjy.tpk2.controller.BaseContoller;
import com.njustjjy.tpk2.dao.BusinessMapper;
import com.njustjjy.tpk2.entity.Business;
import com.njustjjy.tpk2.exception.EmpNotOnlineException;
import com.njustjjy.tpk2.utils.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 配置WebSocketHandler的实现类，让WebSocketSession获取HttpSession
 *
 * */
@Log4j2
@Component
public class EmpWebSocketHandler extends BaseContoller implements WebSocketHandler {

//    @Autowired
//    IBusinessService businessService;
    @Autowired
    BusinessMapper businessMapper;
    //从容器中获取HttpSession对象并设置到
//    Map<String,Object> httpSession = new HashMap<String,Object>();

    //存储全局的webSocketSession对象容器
    private static Map<String,WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<String,WebSocketSession>();

    //用于存储在线的EmpNumber
    private static Vector<String> onlineEmpNumber = new Vector<String>();

    //分配指针：在分配时指针轮转，到最高业务时指针重置
    private static int point = 0;

    //创建一个成功的代码200
    private final static int OK = 200;

    @Override
    //该方法在webSocket建立链接后调用
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //获取httpSession容器中的empNumber
        String empNumber = getEmpNumberinWebSocketSession(session);
        //链接建立的时候将webSocketSession和HttpSession存储到对应的容器中
        webSocketSessionMap.put(empNumber,session);
        //使用Set集合存储在线的EmpNumber
        containerSave(empNumber);

        //后端获取数据
        List<Business> data = businessMapper.queryBusinessByEmpNumber(empNumber);

        //发送消息操作，发送给对应的业务员
        sendMessagetoEmpNumber(data,empNumber);
    }

    @Override
    //获取前端WebSocket发送的消息
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    //处理消息传输中的错误
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**----未使用的方法------*/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //获取empNumber
//        String empNumber = getEmpNumberinWebSocketSession(session);
        //删除数据内容
//        webSocketSessionMap.remove(empNumber);
//        onlineEmpNumber.remove(empNumber);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 将消息发送到对应的Emp客户端
     * @param
     * @param
     * */
    public void sendMessagetoEmpNumber(Object message,String empNumber) throws IOException {
       //封装返回数据
       JsonResult<Object> result = new JsonResult<Object>(OK,message);
       log.info(result);
       //数据转换为JSON字符串
       String json = JSONObject.toJSONString(result);
       //封装消息数据
       TextMessage textMessage = new TextMessage(json.getBytes(StandardCharsets.UTF_8));
       //容器里获得对应的webSocketSession
       WebSocketSession webSocketSession = webSocketSessionMap.get(empNumber);

       //状态判断

       //判断是否获取到webSocket了
       if(webSocketSession == null){
           //未获取到处理
           log.warn("未获取到对应的webSocketSession");
           return;
       }

       //发送时会发生异常
       try{
           webSocketSession.sendMessage(textMessage);
       }catch (IllegalStateException err){
            return;
       }

    }

    /**
     * 分配业务员并发送消息
     * @param
     * @param
     * */
    public void allocationEmp(Business business) throws IOException {

        //判断集合中是否有数据
        if(onlineEmpNumber.size() == 0){
            //抛出异常：没有业务员在线,说明今天不上班或者还没有上班
            throw new EmpNotOnlineException("业务员未上线请耐性等待");
        }

        log.info(onlineEmpNumber.size()-1);

        //开始时指针轮转
        synchronized(this){
            point++;
            //判断长度
            if(point > (onlineEmpNumber.size()-1)){
                point = 0;
            }
        }

        //指针数值
        log.info("指针" + point);

        //获取对应的empNumber
        String empNumber = onlineEmpNumber.get(point);

        //设置数据到对应的对象中
        business.setEmpNumber(empNumber);
        //将数据存储到DB中
        businessMapper.addBusiness(business);

        //后端获取数据
        List<Business> data = businessMapper.queryBusinessByEmpNumber(empNumber);

        //发送
        sendMessagetoEmpNumber(data,empNumber);
    }


    /*-------私有化方法-------*/
    /**
     * 获取对应WebSocketSession中的HttpSession中的empNumber
     */
    //在webSocket中获取empNumber
    private final String getEmpNumberinWebSocketSession(WebSocketSession webSocketSession){
        return getHttpSessioninWebSocket(webSocketSession).get("empNumber").toString();
    }

    //使用webSocket获取对应的HttpSession内容
    private final Map<String,Object> getHttpSessioninWebSocket(WebSocketSession webSocketSession){
        return webSocketSession.getAttributes();
    }

    //存储数据
    private synchronized final void containerSave(String empNumber){
        //判断并存储元素
        if(onlineEmpNumber.contains(empNumber)){
           return;
        }
        //将数据存储到Online中
        onlineEmpNumber.add(empNumber);
    }
}
