package com.zlwon.vo.pc.applicationCase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc物性关联应用案例详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class PcApplicationCaseSimpleVo {

	private Integer id;  //自增ID
	
	private Integer sid;  //物性ID
	
	private String specName;  //物性规格名称
	
	private String manufacturer;  //生产商
	
	private String title;  //案例名称
	
	private String appProduct;  //应用产品
	
	private String photo;  //缩略图
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private String codePath;//二维码路径
}
