package com.njustjjy.tpk2.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  JSON 格式的数据进行响应
 *
 * */

@Data
@ApiModel("API通用返回数据模型")
public class JsonResult<E> implements Serializable {
    //状态码
    @ApiModelProperty("访问状态码")
    private Integer state;
    @ApiModelProperty("服务器对当前状态回复的信息")
    private String message;
    @ApiModelProperty("服务器返回的数据集")
    private E data;

    //构造函数
    public JsonResult(){}
    public JsonResult(Integer state) {
        this.state = state;
    }
    public JsonResult(Throwable err) {
        this.message = err.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Integer state,String message,E data){
        this.state = state;
        this.message = message;
        this.data = data;
    }
}
