package com.zlwon.server.service;

import java.util.List;

import com.zlwon.vo.pc.customer.ApplyCompanyCustomerVo;

/**
 * 企业service
 * @author yuand
 *
 */
public interface CompanyService {

	/**
	 * 根据企业简称关键字，得到所有企业简称名称(只得到审核通过的和type是企业状态的)
	 * @param companyShortName
	 * @return
	 */
	List<String> findCompanyByShortName(String companyShortName);

	/**
	 * 根据企业简称名称，得到该企业简称下所有的企业全称信息
	 * @param companyShortName
	 * @return
	 */
	List<ApplyCompanyCustomerVo> findFullCompanyNameByShortName(String companyShortName);

	/**
	 * 根据企业简称名称和企业全称关键字，得到所有企业全称信息
	 * @param companyShortName 企业简称名称
	 * @param companyFullName 企业全称关键字
	 * @return
	 */
	List<ApplyCompanyCustomerVo> findFullCompanyByShortNameAndFullName(String companyShortName, String companyFullName);

}
