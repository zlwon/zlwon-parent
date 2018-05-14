package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.InfoService;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 资讯相关
 * @author yangy
 *
 */

@AuthLogin
@RestController
@RequestMapping("info")
public class InfoController {

	@Autowired
	private InfoService infoService;
	
	/**
	 * 根据资讯id删除资讯
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteInfoById", method = RequestMethod.GET)
	public ResultData deleteInfoById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int count = infoService.deleteInfoById(id);
		
		return ResultData.ok();
	}
}
