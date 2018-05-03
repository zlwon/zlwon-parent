package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 物性表加工建议
 * @author yangy
 *
 */

@Data
public class ProcessingAdvice{
    
	private Integer id;  //自增ID

    private Integer uid;  //用户Id，如果为0，表示官方数据

    private Integer specId;  //物性ID
    
    private String className;  //主题，用户创建时必填

    private String value;  //值

    private String unit;  //单位

    private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过
}