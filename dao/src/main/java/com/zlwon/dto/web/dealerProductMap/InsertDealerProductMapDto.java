package com.zlwon.dto.web.dealerProductMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 新增经销商可供产品入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InsertDealerProductMapDto {

	private Integer manufacturerId;  //生产商ID

    private Integer brandId;  //商标ID（可供产品）

    private String availableIndustry;  //可供行业

    private String availableArea;  //经销商可供产品表
}
