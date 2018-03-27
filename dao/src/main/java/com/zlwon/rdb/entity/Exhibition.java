package com.zlwon.rdb.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 展会表
 * @author yuand
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class Exhibition  implements Serializable{

	private Integer id;
	
	private  String  name;//展会名称
	
	private  String  content;//展会描述
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private  Date   startDate;//展会开始时间
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private  Date   endDate;//展会结束时间
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private  Date  createTime;//创建时间
	
	private  Integer  del;//标记删除状态，1正常，-1已删除
	
}
