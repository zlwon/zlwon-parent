package com.zlwon.vo.characteristic;

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
public class CharacteristicDetailVo {

	private Integer id;  //自增ID
	
	private Integer specificationId;  //物性ID
	
	private Integer uid;  //用户ID

    private String labelName;  //标签名称

    private Integer hot;  //点赞量
    
    private Integer isSupport;  //是否点赞 1：点赞，2：未点赞
}
