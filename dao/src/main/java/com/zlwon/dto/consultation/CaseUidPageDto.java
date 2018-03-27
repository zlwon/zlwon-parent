package com.zlwon.dto.consultation;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 工程师ID分页入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CaseUidPageDto implements Serializable {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private String openId;  //工程师openID
	
	private String entryKey;  //微信加密字符串
}
