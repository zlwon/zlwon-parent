package com.zlwon.dto.pc.specification;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端新增/删除物性标签点赞记录入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ChangeCharacterRecordDto {

	private Integer characteristicId;  //物性标签ID
}
