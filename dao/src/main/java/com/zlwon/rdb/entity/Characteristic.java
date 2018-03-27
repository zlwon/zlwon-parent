package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Characteristic implements  Serializable{
    private Integer id;

    private Integer uid;

    private String labelName;

    private Integer examine;

    private Integer hot;

}