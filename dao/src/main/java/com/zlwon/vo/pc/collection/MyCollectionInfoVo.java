package com.zlwon.vo.pc.collection;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端我的收藏出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class MyCollectionInfoVo {

	private Integer id;
	
	private Integer infoId;  //信息ID
	
	private String nickname;  //昵称
	
	private String headerimg;  //用户头像
	
	private Integer type;  //信息类型，1物性表，2案例，3提问
	
	private String title;  //显示标题
	
	private Integer uid;  //用户ID
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //收藏日期
}
