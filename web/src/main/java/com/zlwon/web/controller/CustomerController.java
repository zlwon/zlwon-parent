package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultList;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CustomerService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/customer")
@AuthLogin
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	/**
	 * 得到所有正常用户，手机号模糊查询，分页
	 * @param pageIndex
	 * @param pageSize
	 * @param key 手机号模糊查询
	 * @return
	 */
	@RequestMapping(value="queryAllCustomer",method=RequestMethod.GET)
	public  ResultPage   queryAllCustomer(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,String  key){
		PageInfo info = customerService.findAllCustomerPage(pageIndex,pageSize,key);
		return  ResultPage.list(info);
	}
	
	/**
	 * 根据用户id，得到用户详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryCustomerById",method=RequestMethod.GET)
	public  ResultData  queryCustomerById(Integer  id){
		Customer customer = customerService.findCustomerById(id);
		if(customer==null  ||  customer.getDel() != 1){
			//返回数据不存在
			return  ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		customer.setPassword(null);
		return  ResultData.one(customer);
	}
	
	/**
	 * 新增用户
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="addCustomer",method=RequestMethod.POST)
	public  ResultData  addCustomer(Customer  customer){
		customerService.saveCustomerSelective(customer);
		return  ResultData.ok();
	}
	
	/**
	 * 保存修改后的用户信息
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="editCustomerById",method=RequestMethod.POST)
	public  ResultData  editCustomerById(Customer  customer){
		customerService.alterCustomerByIdMake(customer);
		return  ResultData.ok();
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelCustomerById",method=RequestMethod.GET)
	public  ResultData  cancelCustomerById(Integer  id){
		customerService.removeCustomerById(id);
		return  ResultData.ok();
	}
	
	
	/**
	 * 得到所有用户，根据类型获取，不分页,只有企业用户才进行模糊查询企业名称
	 * @param type 账户类型，0普通用户，1知料师，2企业
	 * @param key 关键字，只有企业用户才进行模糊查询企业名称
	 * @return
	 */
	@RequestMapping(value="queryCustomerByType",method=RequestMethod.GET)
	public  ResultData  queryCustomerByType(Integer   type,String  key){
		List<Customer>  list = customerService.findCustomerByType(type,key);
		return  ResultData.one(list);
	}
	
	
	/**
	 * 用户认证-通过用户申请认证信息
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value="customerApplySuccess",method=RequestMethod.GET)
	public   ResultData  customerApplySuccess(Integer   id){
		customerService.alterCustomerApplySuccess(id);
		return  ResultData.ok();
	}
	
	
	
	
	
	
	
}
