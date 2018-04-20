package com.zlwon.vo.pc.collection;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端处理收藏出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class HandleCollectionVo {

	private Integer collectionId;  //收藏ID
	
	private Integer handleType;  //处理类型：1：新增收藏，2：删除收藏
}
