package com.zlwon.rdb.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CMS管理员表
 * @author yuand
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class Sysadmin implements Serializable{
	
	private Integer id;

	private   String  username;//登录账号
	
	private   String  password;//登录密码
	
	private   String  name;//管理员姓名
	
	private   String  mobile;//管理员电话
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private   Date   createTime;//创建时间
	
	private   Integer  del;//标记删除状态，1正常，-1已删除
}
