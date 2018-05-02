package com.zlwon.rdb.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 应用案例用户编辑记录
 * @author yuand
 *
 */
@Data
public class CaseEdit {
    private Integer id;//自增id

    private Integer aid;//案例id 

    private Integer uid;//用户id
    
    private String selectRequirements;//选材要求

    private String selectCause;//选材原因

    private String setting;//选材背景

    private Integer examine;//用户创建数据审核结果，0未审核，1审核通过

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;//审核通过时间

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;//编辑时间

}