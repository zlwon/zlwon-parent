package com.zlwon.vo.applicationCase;

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
 * 得到案例详情出差，主要就是终端客户和应用产品名称映射为中文
 * @author yuand
 *
 */
@Data
public class ApplicationCaseVo  implements Serializable{

	private Integer id;  //自增ID
	
	private Integer uid;  //用户ID
	
	private Integer sid;  //物性ID
	
	private String title;  //案例名称
	
	private Integer terminalId;  //终端客户ID
	private Integer terminal;  //终端客户名称
	
	private Integer appProductId;  //应用产品ID
	private Integer appProduct;  //应用产品名称
	
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
}
