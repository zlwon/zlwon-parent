package com.zlwon.vo.pc.inform;

import lombok.Data;

/**
 * pc消息页，不同label标识不同的消息个数
 * @author yuand
 *
 */
@Data
public class InformLabelVo {

	private  Integer   qNumber;//提问消息个数
	
	private  Integer   aNumber;//回答消息个数
	
	private  Integer   ceNumber;//编辑案例消息个数
	
	private  Integer   chNumber;//物性标签消息个数
	
	private  Integer   dqNumber;//物性报价单消息个数
	
	private  Integer   caNumber;//认证消息个数
}
