package com.njustjjy.tpk2.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class Btprice extends BaseEntity implements Serializable {
    private Integer pid;
    private String parent;
    private String codes;
    private String name;
    private Double price;
    private String describes;
}
