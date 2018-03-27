package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性规格及标签关联表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CharacteristicSpecMap implements Serializable {

	private Integer id;  //自增ID
	
	private Integer specificationId;  //物性ID
	
	private Integer characteristicId;  //标签ID
}
