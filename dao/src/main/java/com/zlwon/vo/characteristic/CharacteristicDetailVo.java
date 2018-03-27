package com.zlwon.vo.characteristic;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性规格及标签关联详细出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CharacteristicDetailVo implements Serializable {

	private Integer id;  //自增ID
	
	private Integer specificationId;  //物性ID
	
	private Integer characteristicId;  //标签ID
	
	private Integer uid;  //用户ID

    private String labelName;  //标签名称

    private Integer hot;  //点赞量
}
