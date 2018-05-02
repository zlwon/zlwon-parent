package com.zlwon.vo.pc.questions;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端问题详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QuestionsDetailVo {

	private Integer id;
	
	private Integer uid;  //用户ID
	
	private String nickname;  //昵称
	
	private String headerimg;  //用户头像
	
	private String intro;  //一句话简介
	
	private Integer iid;  //信息ID
	
	private String source;  //来源名称
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private Integer moduleType;  //模块类型
	
	private String title;  //提问标题
	
	private String content;  //问题内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer answerCount;  //回答数量
	
	private Integer isCollect;  //是否收藏 1：是，0：否
	
	private Integer collectId;  //收藏ID
	
	private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过，2驳回；先通过后审核机制
}
