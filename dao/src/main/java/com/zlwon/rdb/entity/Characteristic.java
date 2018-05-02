package com.zlwon.rdb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性表主要特性标签表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class Characteristic{
    
	private Integer id;  //自增ID

    private Integer uid;  //用户ID
    
    private Integer specId;  //物性规格ID

    private String labelName;  //标签名称

    private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过

    private Integer hot;  //点赞量

}