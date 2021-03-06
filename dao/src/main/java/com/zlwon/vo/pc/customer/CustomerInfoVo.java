package com.zlwon.vo.pc.customer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 关注前查询用户信息
 * @author yuand
 *
 */
@Data
public class CustomerInfoVo {

	private  String  nickname;//昵称
	
	private  String  headerimg;//头像
	
	private  String  intro;//一句话介绍
	
	private  String  company;//公司名称
	
	private  Integer  attentionNum;//关注者个数
	
	private  Integer  attention;//是否关注0未关注1已关注
	
	private  Integer  isoneself;//是否是自己0不是1是（如果是自己，就不显示关注按钮）
	
	@JsonIgnore
	private  String   labelIds;//被关注者业务标签id
	
	private  List<String>  label;//被关注者业务标签
}
