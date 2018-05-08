package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.rdb.dao.CompanyMapper;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.entity.Company;
import com.zlwon.server.service.CompanyService;
import com.zlwon.vo.pc.customer.ApplyCompanyCustomerVo;

/**
 * 企业service
 * @author yuand
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private   CompanyMapper   companyMapper;
	@Autowired
	private   CustomerMapper   customerMapper;
	
	/**
	 * 根据企业简称关键字，得到所有企业简称名称
	 * @param companyShortName
	 * @return
	 */
	@Override
	public List<String> findCompanyByShortName(String companyShortName) {
		List<String>  list = companyMapper.selectCompanyByShortNameExamine(companyShortName);
		return list;
	}

	/**
	 * 根据企业简称名称，得到该企业简称下所有的企业全称信息
	 * @param companyShortName
	 * @return
	 */
	@Override
	public List<ApplyCompanyCustomerVo> findFullCompanyNameByShortName(String companyShortName) {
		//根据企业简称名称查看是否存在企业简称(只得到审核通过的)
		Company  company = customerMapper.selectCompanyByShortNameExamine(companyShortName);
		if(company != null){
			List<ApplyCompanyCustomerVo>  list = companyMapper.selectAllCompanyByShortNameExamine(company.getId(),company.getStatus());
			return  list;
		}
		return null;
	}

	/**
	 * 根据企业简称名称和企业全称关键字，得到所有企业全称信息
	 * @param companyShortName 企业简称名称
	 * @param companyFullName 企业全称关键字
	 * @return
	 */
	@Override
	public List<ApplyCompanyCustomerVo> findFullCompanyByShortNameAndFullName(String companyShortName,
			String companyFullName) {
		//根据企业简称名称查看是否存在企业简称(只得到审核通过的)
		Company  company = customerMapper.selectCompanyByShortNameExamine(companyShortName);
		if(company != null){
			List<ApplyCompanyCustomerVo>  list = companyMapper.selectCompanyByParentIdAndFullNameExamine(companyFullName,company.getId(),company.getStatus());
			return  list;
		}
		return null;
	}

	
	
}
