package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票活动参与项目信息点评记录表（mysql）实体
 * @author yangy
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class VoteProjectMessage implements Serializable {

	private Integer id;  //自增ID
	
	private Integer uid;  //点评用户ID
	
	private Integer iid;  //参与活动信息ID
	
	private Integer aid;  //活动ID
	
	private String messageInfo;  //点评内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
}
