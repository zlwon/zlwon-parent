package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 个人业务标签表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CharacteristicBusiness {
    
	private Integer id;

    private String labelName;  //标签名称

    private Integer parentId;  //父标签ID
}