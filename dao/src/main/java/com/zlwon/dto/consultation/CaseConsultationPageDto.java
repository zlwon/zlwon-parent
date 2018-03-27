package com.zlwon.dto.consultation;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 根据案例ID查询咨询分页入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CaseConsultationPageDto implements Serializable {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer caseId;  //案例ID
	
	private String entryKey;  //微信加密字符串
	
	private String openId;  //微信openId
}
