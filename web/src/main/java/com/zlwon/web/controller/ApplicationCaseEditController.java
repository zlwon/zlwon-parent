package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseEditService;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 案例编辑api
 * @author yuand
 *
 */
@AuthLogin
@RestController
@RequestMapping("applicationCaseEdit")
public class ApplicationCaseEditController {
	
	@Autowired
	private  ApplicationCaseEditService  applicationCaseEditService;

	/**
	 * 得到所有编辑案例信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllApplicationCaseEdit",method=RequestMethod.GET)
	public   ResultPage   queryAllApplicationCaseEdit(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize){
		PageInfo  info = applicationCaseEditService.findAllApplicationCaseEdit(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 设置编辑案例通过(需要添加到通知表)
	 * @param id
	 * @return
	 */
	@RequestMapping(value="editApplicationCaseEditSuccess",method=RequestMethod.POST)
	public  ResultData  editApplicationCaseEditSuccess(Integer  id){
		applicationCaseEditService.alterApplicationCaseEditSuccess(id);
		return  ResultData.ok();
	}
	
	
	/**
	 * 设置编辑案例驳回(需要添加到通知表)
	 * @param id
	 * @return
	 */
	@RequestMapping(value="editApplicationCaseEditFailed",method=RequestMethod.POST)
	public  ResultData  editApplicationCaseEditFailed(Integer  id,String  content){
		applicationCaseEditService.alterApplicationCaseEditFailed(id,content);
		return  ResultData.ok();
	}
	
	
	/**
	 * 得到编辑案例驳回信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryApplicationCaseEditFailedContent",method=RequestMethod.GET)
	public  ResultData  queryApplicationCaseEditFailedContent(Integer  id){
		String str = applicationCaseEditService.findApplicationCaseEditFailedContent(id);
		return  ResultData.one(str);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
