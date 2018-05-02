package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseEditService;

/**
 * 案例编辑api
 * @author yuand
 *
 */
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
}
