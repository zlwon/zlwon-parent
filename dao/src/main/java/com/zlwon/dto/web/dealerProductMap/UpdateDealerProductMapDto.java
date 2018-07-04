package com.zlwon.dto.web.dealerProductMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 编辑经销商可供产品入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class UpdateDealerProductMapDto {
	
	private Integer id;  //ID

	private String availableIndustry;  //可供行业

    private String availableArea;  //经销商可供产品表
}
