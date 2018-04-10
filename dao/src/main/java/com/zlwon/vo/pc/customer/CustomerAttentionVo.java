package com.zlwon.vo.pc.customer;

import lombok.Data;

/**
 * 我关注的(关注我的)人信息
 * @author yuand
 *
 */
@Data
public class CustomerAttentionVo {

	private  Integer  id;//id
	
	private  String   nickname;//昵称
	
	private  String   headerimg;//头像
	
	private  String   intro;//一句话介绍
}
