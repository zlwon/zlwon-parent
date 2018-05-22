package com.zlwon.dto.api.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端根据物性表ID查询行情与渠道入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryWCSpecDealerdDto {

	private Integer specId;  //物性表ID
}
