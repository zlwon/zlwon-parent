package com.zlwon.vo.applicationCase;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 应用案例部分数据出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ApplicationCaseSimpleVo implements Serializable {

	private Integer id;  //自增ID
	
	private String title;  //案例名称
	
	private String terminal;  //终端客户
	
	private String specName;  //规格名称
	
	private String appProduct;  //应用产品
	
	private String photo;  //缩略图
	
	private String album;  //相册
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer exhibitionId;  //展会ID
	
	private Integer exhibitionCaseId; //展会案例ID
}
