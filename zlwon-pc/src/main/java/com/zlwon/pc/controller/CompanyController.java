package com.zlwon.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CompanyService;
import com.zlwon.vo.pc.customer.ApplyCompanyCustomerVo;

/**
 * 企业api
 * @author yuand
 *
 */
@RestController
@RequestMapping("pc/company")
public class CompanyController {
	
	@Autowired
	private  CompanyService  companyService;
	
	/**
	 * 根据企业简称关键字，得到所有企业简称名称
	 * @param companyShortName
	 * @return
	 */
	@RequestMapping(value="queryCompanyByShortName",method=RequestMethod.POST)
	public   ResultData  queryCompanyByShortName(String   companyShortName){
		List<String>  list = companyService.findCompanyByShortName(companyShortName);
		return   ResultData.one(list);
	}
	
	/**
	 * 根据企业简称名称，得到该企业简称下所有的企业全称信息
	 * @param companyShortName
	 * @return
	 */
	@RequestMapping(value="queryFullCompanyNameByShortName",method=RequestMethod.POST)
	public  ResultData  queryFullCompanyNameByShortName(String   companyShortName){
		List<ApplyCompanyCustomerVo>  list = companyService.findFullCompanyNameByShortName(companyShortName);
		return   ResultData.one(list);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
