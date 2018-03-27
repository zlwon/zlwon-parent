package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 案例管理api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/case")
@AuthLogin
public class ApplicationCaseController {

	@Autowired
	private ApplicationCaseService applicationCaseService;
	
	/**
	 * 得到所有案例
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllApplicateCase",method=RequestMethod.GET)
	public   ResultPage   queryAllApplicateCase(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer  pageSize){
		PageInfo<ApplicationCase> info = applicationCaseService.findAllApplicationCase(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 根据案例id，得到案例详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryApplicateCaseById",method=RequestMethod.GET)
	public   ResultData   queryApplicateCaseById(Integer  id){
		ApplicationCase applicationCase = applicationCaseService.findAppCaseById(id);
		if(applicationCase==null  || applicationCase.getDel()!=1){
			return  ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		return  ResultData.one(applicationCase);
	}
	
	/**
	 * 根据案例id，保存修改后的案例信息
	 * @param applicationCase
	 * @return
	 */
	@RequestMapping(value="editApplicateCaseById",method=RequestMethod.POST)
	public  ResultData   editApplicateCaseById(ApplicationCase  applicationCase){
		applicationCaseService.alterApplicateCaseById(applicationCase);
		return  ResultData.ok();
	}
	
	/**
	 * 根据案例id，删除指定的案例
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelApplicateCaseById",method=RequestMethod.GET)
	public  ResultData  cancelApplicateCaseById(Integer  id){
		applicationCaseService.removeApplicateCaseById(id);
		return  ResultData.ok();
	}
	
	/**
	 * 添加案例信息
	 * @param applicationCase
	 * @return
	 */
	@RequestMapping(value="addApplicateCase",method=RequestMethod.POST)
	public  ResultData  addApplicateCase(ApplicationCase  applicationCase){
		applicationCaseService.saveApplicateCase(applicationCase);
		return ResultData.ok();
	}
}
