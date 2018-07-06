package com.zlwon.vo.pc.dealerdQuotation;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 经销商可供产品详细出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class DealerProductMapDetailVo {

	private Integer id;
	
	private Integer manufacturerId;  //生产商ID
	
	private String manufacturer;  //生产商

    private Integer brandId;  //商标ID（可供产品）
    
    private String brand;  //商标（可供产品）

    private String availableIndustry;  //可供行业

    private String availableArea;  //经销商可供产品表
    
    private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过，2驳回

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;  //创建时间
    
    private Integer uid;  //用户ID
    
    private String company;  //公司名称
    
    private String linkman;  //联系人
    
    private String mobile;  //联系电话
    
    private String mail;  //联系邮箱
}
