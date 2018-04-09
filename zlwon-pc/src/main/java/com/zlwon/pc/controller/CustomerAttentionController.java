package com.zlwon.pc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.user.CustomerAttentionDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CustomerAttentionService;

import io.swagger.annotations.Api;

/**
 * 关注api
 * @author yuand
 *
 */
@AuthLogin
@Api
@RestController
@RequestMapping("/pc/customerAttention")
public class CustomerAttentionController {

	@Autowired
	private  CustomerAttentionService   customerAttentionService;
	
	/**
	 * 用户关注
	 * @param request
	 * @param id 被关注者id
	 * @return
	 */
	@RequestMapping(value="addCustomerAttention",method=RequestMethod.POST)
	public   ResultData  addCustomerAttention(HttpServletRequest  request,Integer  id){
		customerAttentionService.saveCustomerAttention(request,id);
		return  ResultData.ok();
	}
	
	/**
	 * 用户取消关注
	 * @param request
	 * @param id 被关注者id
	 * @return
	 */
	@RequestMapping(value="cancelCustomerAttention",method=RequestMethod.GET)
	public  ResultData  cancelCustomerAttention(HttpServletRequest  request,Integer  id){
		customerAttentionService.removeCustomerAttention(request,id);
		return  ResultData.ok();
	}
	
	/**
	 * 得到我关注的人,分页获取
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryMyAttention",method=RequestMethod.GET)
	public  ResultPage  queryMyAttention(HttpServletRequest  request,@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize){
		PageInfo  info = customerAttentionService.findMyAttention(request,pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 得到关注我的人，分页获取
	 * @param request
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAttentionMy",method=RequestMethod.GET)
	public  ResultPage  queryAttentionMy(HttpServletRequest  request,@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize){
		PageInfo  info = customerAttentionService.findAttentionMy(request,pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	
	
	
	
}
