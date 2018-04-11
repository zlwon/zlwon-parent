package com.zlwon.vo.pc.specification;

import java.io.Serializable;
import java.util.List;

import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.SpecificationParameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端物性表搜索筛选信息头部出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class SpecSearchHeaderVo implements Serializable {

	private List<SpecificationParameter> brandNameList;  //获取全部商标
	
	private List<SpecificationParameter> baseMaterialList;  //获取全部基材
	
	private List<Customer> manufacturerList;  //获取全部生产商
	
	private List<SpecificationParameter> fillerList;  //获取全部填充物
	
	private List<SpecificationParameter> safeCertifyList;  //获取全部安规认证
}
