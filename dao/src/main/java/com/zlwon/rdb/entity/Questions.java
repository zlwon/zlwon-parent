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
 * 提问（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class Questions  implements Serializable{
	
	private Integer id;

	private String nsid;  //NoSqlID
	
	private Integer uid;  //用户ID
	
	private Integer iid;  //信息ID
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private Integer moduleType;  //模块类型
	
	private String title;  //提问标题
	
	private String content;  //问题内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过；先通过后审核机制
}
