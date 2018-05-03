package com.zlwon.vo.characteristic;

import lombok.Data;

/**
 * web物性标签列表
 * @author yuand
 *
 */
@Data
public class CharacteristicListVo {

	private   Integer  id;//物性标签id
	
	private String labelName;  //标签名称

	private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过，2驳回
	
	private Integer hot;  //点赞量
	
	private String  nickname;//用户昵称
	
	private String  headerimg;//用户头像
	
	private  String  name;//标签关联的物性规格名称
}
