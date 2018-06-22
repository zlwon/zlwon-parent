package com.zlwon.mobile.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.mobile.annotations.AuthLogin;
import com.zlwon.rdb.entity.CharacteristicBusiness;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.vo.pc.customer.PcCustomerDetailVo;
import com.zlwon.vo.pc.customer.ProducerVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 移动端官网用户接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/mobile/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	/**
	 * 得到所有生产商，不分页
	 * @return
	 */
	@RequestMapping(value = "queryProducer", method = RequestMethod.GET)
	public  ResultData  queryProducer(){
		List<ProducerVo> list = customerService.findProducer();
		return  ResultData.one(list);
	}
	
	/**
	 * 根据token查询用户详情
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "根据token查询用户详情")
    @RequestMapping(value = "/queryCustomerInfoByToken", method = RequestMethod.GET)
	public ResultData queryCustomerInfoByToken(HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		Integer isLogin = 0;
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
		if(user == null){
			/*return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);*/
			isLogin = 0;
		}else{
			PcCustomerDetailVo result = customerService.findCustomerDetailById(user.getId());
			if(result == null){
				isLogin = 0;
			}else{
				isLogin = 1;
			}
		}
		
		return ResultData.one(isLogin);
	}
}
