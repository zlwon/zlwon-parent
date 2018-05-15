package com.zlwon.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.AccessPage;
import com.zlwon.rdb.entity.Info;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.AccessPageService;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 首页访问记录相关
 * @author yangy
 *
 */

@AuthLogin
@RestController
@RequestMapping("accessPage")
public class AccessPageController {

	@Autowired
	private AccessPageService accessPageService;
	
	/**
	 * 查询所有页面访问记录
	 * @return
	 */
	@RequestMapping(value = "queryAllAccessPage", method = RequestMethod.GET)
	public ResultData queryAllAccessPage(){
		
		List<AccessPage> list = accessPageService.findAllAccessPage();
		
		return ResultData.one(list);  
	}
}
