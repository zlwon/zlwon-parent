package com.zlwon.vo.pc.applicationCase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 案例详情页
 * @author yuand
 *
 */
@Data
public class ApplicationCaseDetailsVo {
	
	private Integer id;  //案例自增ID
	
	private Integer sid;  //物性ID
	
	private String name;  //物性规格名称
	
	private String baseMaterial;  //物性基材
	
	private String title;  //案例名称
	
	private String terminal;  //终端客户
	
	private String appProduct;  //应用产品
	
	private String selectRequirements;  //选材要求
	
	private String selectCause;  //选材原因
	
	private String setting;  //案例背景
	
	private String photo;  //缩略图
	
	private String album;  //相册
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer uid;  //创建案例用户的ID
	
	private String mobile;  //创建案例用户的联系方式
	
	private String nickname;  //创建案例用户的昵称
	
	private String headerimg;  //创建案例用户的头像
}
