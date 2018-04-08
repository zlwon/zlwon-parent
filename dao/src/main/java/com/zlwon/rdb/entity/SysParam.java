package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 系统参数（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor 
public class SysParam implements Serializable {
	
    private Integer id;

    private String paramName;  //参数名称

    private String paramValue;  //参数值
    
    private Integer type;  //类别
}