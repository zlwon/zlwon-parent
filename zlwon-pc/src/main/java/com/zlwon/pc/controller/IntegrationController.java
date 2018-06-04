package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.integration.QueryMyIntegrationDeatilMapPageDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.IntegrationDeatilMapService;
import com.zlwon.vo.pc.integration.IntegrationDetailMapVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 积分pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/integration")
public class IntegrationController extends BaseController {

	@Autowired
	private IntegrationDeatilMapService integrationDeatilMapService;
	
	/**
	 * pc端分页查询当前用户异动明细流水记录
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端分页查询当前用户异动明细流水记录")
    @RequestMapping(value = "/queryMyIntegrationDeatilMapPage", method = RequestMethod.POST)
	public ResultPage queryMyIntegrationDeatilMapPage(QueryMyIntegrationDeatilMapPageDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询
		PageInfo<IntegrationDetailMapVo> pageList = integrationDeatilMapService.findMyIntegrationDeatilMapByPage(form);
		
		return ResultPage.list(pageList);
	}
}
