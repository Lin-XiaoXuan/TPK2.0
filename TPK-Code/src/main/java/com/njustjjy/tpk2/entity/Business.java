package com.njustjjy.tpk2.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *业务类实体类
 * -- 晓轩
 * */
@Data
public class Business extends BaseEntity implements Serializable {
    private Integer bid;
    private String recordNumber;
    private String empNumber;
    private String numbers;
    private String phone;
    private String type;
    private Integer way;
    private Integer progress;
    private Double money;
    private Integer isDelete;
    private LocalDateTime executeTime;
}
