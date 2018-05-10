package com.zlwon.rdb.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Company {
    private Integer id;

    private String name;

    private Integer parentId;

    private Byte status;

    private String charter;

    private String intro;

    private String linkPerson;

    private String linkEmail;

    private String linkTel;

    private Integer uid;

    private Byte examine;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;

}