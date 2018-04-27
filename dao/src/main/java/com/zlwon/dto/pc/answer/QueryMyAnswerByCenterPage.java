package com.zlwon.dto.pc.answer;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询我的回答-个人中心入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryMyAnswerByCenterPage {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer userId;  //用户ID（前端不传）
}
