package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Data;

/**
 * 案例编辑
 * @author yuand
 *
 */
@Data
public class ApplicationCaseEdit {
    private Integer id;

    private Integer aid;

    private Integer uid;

    private Integer examine;

    private Date auditTime;

    private Date createTime;

    private String selectRequirements;

    private String selectCause;

    private String setting;
}