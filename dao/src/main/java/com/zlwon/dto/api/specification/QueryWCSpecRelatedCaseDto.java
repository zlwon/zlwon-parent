package com.zlwon.dto.api.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端根据物性表ID查询物性关联案例入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryWCSpecRelatedCaseDto {

	private Integer specId;  //物性表ID
}
