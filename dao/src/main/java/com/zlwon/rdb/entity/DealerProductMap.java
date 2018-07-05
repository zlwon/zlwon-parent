package com.zlwon.rdb.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 经销商可供产品表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class DealerProductMap {
    
	private Integer id;

    private Integer uid;  //用户ID（经销商ID）

    private Integer manufacturerId;  //生产商ID

    private Integer brandId;  //商标ID（可供产品）

    private String availableIndustry;  //可供行业

    private String availableArea;  //可供区域
    
    private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过，2驳回

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;  //创建时间
}