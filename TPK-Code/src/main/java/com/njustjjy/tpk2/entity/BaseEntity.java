package com.njustjjy.tpk2.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *实体类基类
 * -- 晓轩
 * */
@Data
public class BaseEntity implements Serializable {
     LocalDateTime createdTime;
     String createdUser;
     LocalDateTime modifiedTime;
     String modifiedUser;
     String remark;
}
