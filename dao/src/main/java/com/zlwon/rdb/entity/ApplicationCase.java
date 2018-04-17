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
 * 应用案例（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ApplicationCase  implements Serializable{

	private Integer id;  //自增ID
	
	private Integer uid;  //用户ID
	
	private Integer sid;  //物性ID
	
	private String title;  //案例名称
	
	private Integer terminalId;  //终端客户ID
	
	private Integer appProductId;  //应用产品ID
	
	private Integer industryId;  //应用行业ID
	
	private Integer marketId;  //应用市场ID
	
	private String selectRequirements;  //选材要求
	
	private String selectCause;  //选材原因
	
	private String setting;  //案例背景
	
	private String photo;  //缩略图
	
	private String album;  //相册
	
	private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过
	
	private Integer del;  //标记删除状态，1正常，-1已删除
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private  String   codePath;//二维码路径
	
	private  Integer   hot;//案例是否热门0否1是
}
