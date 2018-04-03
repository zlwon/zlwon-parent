package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性表经销商报价记录（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class DealerdQuotation implements Serializable {

	private Integer id;  //自增ID
	
	private Integer uid;  //企业账户类型用户ID
	
	private Integer sid;  //物性表Id
	
	private Float price;  //报价
	
	private Date updateTime;  //报价更新日期
	
	private Date createTime;  //创建日期
}
