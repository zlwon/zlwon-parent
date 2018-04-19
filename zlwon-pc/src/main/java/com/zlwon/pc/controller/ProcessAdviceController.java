package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.processAdvice.QueryMyProcessAdviceByPageDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ProcessingAdviceService;
import com.zlwon.server.service.RedisService;
import com.zlwon.vo.pc.processAdvice.ProcessingAdviceDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 加工建议pc端接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/processAdvice")
public class ProcessAdviceController extends BaseController {
	
	@Autowired
	private ProcessingAdviceService processingAdviceService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * pc端分页查询我的加工建议
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端分页查询我的加工建议")
    @RequestMapping(value = "/queryMyProcessAdviceByPage", method = RequestMethod.POST)
    public ResultPage queryMyProcessAdviceByPage(QueryMyProcessAdviceByPageDto form,HttpServletRequest request){
		
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
		
		if(currentPage == null || pageSize == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我的加工建议
		PageInfo<ProcessingAdviceDetailVo> pageList = processingAdviceService.findProcessAdviceByUserIdPage(form);
		
		return ResultPage.list(pageList);
	}
}
