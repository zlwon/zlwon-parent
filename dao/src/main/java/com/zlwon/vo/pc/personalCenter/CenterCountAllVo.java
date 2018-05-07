package com.zlwon.vo.pc.personalCenter;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端个人中心标签栏统计数量出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CenterCountAllVo {

	private Integer myQuestionCount;  //我的问题总数
	
	private Integer myAnswerCount;  //我的回答总数
	
	private Integer myCollectCount;  //我的收藏总数
	
	private Integer myEditCaseCount;  //我的编辑案例总数
}
