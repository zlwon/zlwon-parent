package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 访问记录统计表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AccessPage {
	
    private Integer id;  //自增ID

    private Integer type;  //访问类型

    private String typeName;  //访问类型名称

    private Integer count;  //统计数量
}