package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 展会案例关联表（mysql）实体
 * @author yangy
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class ExhibitionCaseMap  implements Serializable{

	private Integer id;
	
	private Integer caseId;
	
	private Integer exhibitionId;
	
	private Integer exhibitionCaseId;
}
