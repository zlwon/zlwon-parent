package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SpecificationParameter implements  Serializable{
    private Integer id;

    private String name;

    private Integer classType;

}