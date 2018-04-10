package com.zlwon.pc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.user.CustomerInfoDto;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.customer.CustomerDetailVo;
import com.zlwon.vo.specification.SpecificationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/pc/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	/**
	 * 根据用户id，得到用户信息，关注前查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryCustomer",method=RequestMethod.GET)
	public ResultData queryCustomer(HttpServletRequest  request,Integer  id){
		CustomerInfoDto customerInfoDto = customerService.findCustomerInfoByIdMake(request,id);
		return ResultData.one(customerInfoDto);
	}
	
	/**
	 * 根据物性ID查询物性表详情
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据token查询用户详情")
    @RequestMapping(value = "/queryCustomerInfoByToken", method = RequestMethod.GET)
	public ResultData queryCustomerInfoByToken(HttpServletRequest request){
		
		//验证token
		String token = request.getParameter("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		CustomerDetailVo result = customerService.findCustomerDetailById(user.getId());
		
		return ResultData.one(result);
	}
	
	
	
	
	
	
	
}
