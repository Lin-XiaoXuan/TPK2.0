/**
 * 常量表文件
 */

//1、服务器Http请求地址
const serverURL = "127.0.0.1";
const serverPort = "8080"
//不同的协议访问服务器的地址拼接
const serverHttpURL = "http://" + serverURL + ":" + serverPort;
const serverWebSocketURL = "ws://" + serverURL + ":" + serverPort;