package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 提问回答（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class Answer implements Serializable{

	private Integer id;
	
	private Integer uid;  //用户ID
	
	private Integer qid;  //问题ID
	
	private String content;  //回答内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
	
	private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过
	
	private Integer supportNums;//点赞总数
	
	private Integer isAnonymous;  //是否匿名  0：非匿名 1：匿名
}
