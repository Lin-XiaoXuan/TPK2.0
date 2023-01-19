package com.njustjjy.tpk2.entity.other;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *封装一个BusinessTable返回类型
 *
 * */
@Data
public class BusinessTable implements Serializable{
    private String name;
    private Integer age;
    private String businessNumbers;
    private String recordNumber;
    private String phone;
    private String businessTypeName;
    private String businessTypeNumber;
    private Integer gender;
    private LocalDateTime createdTime;
    private String remark;
    private LocalDateTime executeTime;
    private Double money;
}
