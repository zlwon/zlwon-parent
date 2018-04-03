package com.zlwon.dto.pc.collection;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 新增用户收藏入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class PcInsertCollectionDto implements Serializable {

	private Integer type;  //信息类型，1物性表，2案例，3提问
	
	private Integer iid;  //信息ID
}
