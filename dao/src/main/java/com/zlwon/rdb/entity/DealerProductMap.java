package com.zlwon.rdb.entity;

import java.util.Date;

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

    private String availableArea;  //经销商可供产品表
}