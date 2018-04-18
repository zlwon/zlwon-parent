package com.zlwon.vo.pc.applicationCase;

import java.util.Date;

import lombok.Data;

/**
 * 用户提交的案例信息和编辑过的案例信息
 * @author yuand
 *
 */
@Data
public class CustomerApplicationCaseVo {

	private   Integer  id;//案例id，无论是编辑案例还是提交的案例
	
	private   Date   createTime;//创建时间
	
	private   String  title;//案例标题
	
	private   Integer  examine;//用户创建(修改)数据审核结果，0未审核，1审核通过
	
	private  Integer  make;//0编辑案例1创建案例
}
