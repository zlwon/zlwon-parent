package com.zlwon.dto.api.collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 操作收藏入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class OperateCollectionDto {

	private Integer type;  //信息类型，1物性表，2案例，3提问
	
	private Integer iid;  //信息ID
}
