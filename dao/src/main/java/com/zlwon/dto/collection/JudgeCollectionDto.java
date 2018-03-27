package com.zlwon.dto.collection;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 判断用户是否收藏入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class JudgeCollectionDto implements Serializable {

	private Integer type;  //信息类型，1物性表，2案例，3提问
	
	private String openId;  //用户openID
	
	private String entryKey;  //微信加密字符串
	
	private Integer iid;  //信息ID
}
