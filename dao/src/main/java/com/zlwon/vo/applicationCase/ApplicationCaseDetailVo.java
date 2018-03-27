package com.zlwon.vo.applicationCase;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 应用案例详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ApplicationCaseDetailVo implements Serializable {

	private Integer id;  //自增ID
	
	private Integer uid;  //用户ID
	
	private Integer sid;  //物性ID
	
	private String headerimg;  //用户头像
	
	private String nickname;  //昵称
	
	private String specName;  //规格名称
	
	private String title;  //案例名称
	
	private String terminal;  //终端客户
	
	private String appProduct;  //应用产品
	
	private String baseMaterial;  //基材
	
	private String manufacturer;  //生产商
	
	private String selectRequirements;  //选材要求
	
	private String selectCause;  //选材原因
	
	private String setting;  //案例背景
	
	private String photo;  //缩略图
	
	private String album;  //相册
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
}
