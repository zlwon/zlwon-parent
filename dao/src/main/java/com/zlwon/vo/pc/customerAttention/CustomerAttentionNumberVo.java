package com.zlwon.vo.pc.customerAttention;

import lombok.Data;

/**
 * 用户关注和被关注的个数统计
 * @author yuand
 *
 */
@Data
public class CustomerAttentionNumberVo {

	private  Integer   parentNumber;//我关注的总个数
	
	private  Integer   myNumber;//关注我的总个数
}
