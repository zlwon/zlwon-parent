package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票活动参与项目信息（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class VoteProject implements Serializable {

	private Integer id;  //自增ID
	
	private Integer aid;  //活动表ID
	
	private String photo;  //图片
	
	private String smallPhoto;  //压缩图片
	
	private Integer fileType;  //文件类型  1：图片  2：视频
	
	private String title;  //信息标题
	
	private Integer uid;  //创建用户ID
	
	private Integer supportNums;  //点赞量
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
	
	private Integer examine; //用户创建数据审核结果，0未审核，1审核通过
}
