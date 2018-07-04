package com.zlwon.dto.web.dealerProductMap;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * web端批量新增经销商可供产品入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InsertDealerProductMapBatchWebDto {

	private List<InsertDealerProductMapDto> formList;  //批量新增经销商可供产品List
	
	private Integer userId;  //用户ID
}
