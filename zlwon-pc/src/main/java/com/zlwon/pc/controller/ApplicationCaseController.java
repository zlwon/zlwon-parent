package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CollectionService;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;

/**
 * 案例api
 * @author yuand
 *
 */
@AuthLogin
@RestController
@RequestMapping("/pc/applicationCase")
public class ApplicationCaseController {

	@Autowired
	private   ApplicationCaseService   applicationCaseService;
	@Autowired
	private   CollectionService   collectionService;
	
	
	/**
	 * 根据案例id，得到案例详情
	 * @return 案例id
	 */
	@RequestMapping(value="queryApplicationCaseDetails",method=RequestMethod.GET)
	public   ResultData   queryApplicationCaseDetails(Integer  id){
		ApplicationCaseDetailsVo   record = applicationCaseService.findApplicationCaseDetailsMake(id);
		return   ResultData.one(record);
	}
	
	
	/**
	 * 用户修改案例信息
	 * @param request
	 * @param applicationCase
	 * 			id:案例id，必传
	 * 			selectRequirements:选材要求,未修改传null
	 * 			selectCause:选材原因,未修改传null
	 * 			setting:案例背景,未修改传null
	 * @return
	 */
	@RequestMapping(value="editApplicationCase",method=RequestMethod.POST)
	public  ResultData  editApplicationCase(HttpServletRequest  request,ApplicationCase  applicationCase){
		applicationCaseService.alterApplicationCaseByUser(request,applicationCase);
		return   ResultData.ok();
	}
	
	/**
	 * 用户添加案例
	 * @param request
	 * @param applicationCase
	 * @return
	 */
	@RequestMapping(value="addApplicationCase",method=RequestMethod.POST)
	public  ResultData  addApplicationCase(HttpServletRequest  request,ApplicationCase  applicationCase){
		applicationCaseService.saveApplicateCase(request,applicationCase,0);
		return   ResultData.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
