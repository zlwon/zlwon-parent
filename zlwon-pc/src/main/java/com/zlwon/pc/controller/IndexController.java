package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.AccessPageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * pc端主页相关接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/index")
public class IndexController extends BaseController {

	@Autowired
	private AccessPageService accessPageService;
	
	/**
	 * pc端统计访问主页量
	 * @param type
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端统计访问主页量")
    @RequestMapping(value = "/statisticAccessPageCount", method = RequestMethod.GET)
	public ResultData statisticAccessPageCount(@RequestParam Integer type,HttpServletRequest request){
		
		//验证参数
		if(type == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int count = accessPageService.updateCountByType(type);
		
		return ResultData.ok();
	}
}
