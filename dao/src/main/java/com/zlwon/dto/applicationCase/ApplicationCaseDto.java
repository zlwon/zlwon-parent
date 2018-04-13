package com.zlwon.dto.applicationCase;

import lombok.Data;

/**
 * 后端添加和修改案例dto,
 * pc和后端都使用到了
 * @author yuand
 *
 */
@Data
public class ApplicationCaseDto {

	private Integer id;  //自增ID
	
	private Integer uid;  //用户ID
	
	private Integer sid;  //物性ID
	
	private String title;  //案例名称
	
	private Integer terminalId;  //终端客户ID
	
	private String terminal;  //终端客户
	
	private Integer appProductId;  //应用产品ID
	
	private String appProduct;  //应用产品
	
	private Integer industryId;  //应用行业ID
	
	private Integer marketId;  //应用市场ID
	
	private String selectRequirements;  //选材要求
	
	private String selectCause;  //选材原因
	
	private String setting;  //案例背景
	
	private String photo;  //缩略图
	
	private String album;  //相册
}
