package com.zlwon.dto.api.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端新增/删除物性标签点赞记录入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ChangeCharacterRecordApiDto {

	private Integer characteristicId;  //物性标签ID
}
