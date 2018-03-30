package com.zlwon.dto.exhibition;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * api查询特定展会案例入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class SearchSpecifyExhibitionDto implements Serializable {

	private Integer exhibitionId;  //展会ID
	
	private String caseName;  //应用产品（模糊）
	
	private Integer manufacturerId;  //生产商
	
	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private String entryKey;  //微信加密字符串
}
