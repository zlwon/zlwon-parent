package com.zlwon.dto.pc.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端新增物性标签入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InsertSpecCharacterDto {

	private Integer specId;  //物性规格ID
	
	private String labelName;  //标签名称
}
