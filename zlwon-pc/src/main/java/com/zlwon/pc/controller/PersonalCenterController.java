package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.AnswerService;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.vo.pc.personalCenter.CenterCountAllVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 个人中心pc端接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/personalCenter")
public class PersonalCenterController extends BaseController {

	@Autowired
	private QuestionsService questionsService;
	
	@Autowired
	private AnswerService answerService;
	
	/**
	 * pc端根据token查询个人中心标签栏统计数量
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端根据token查询个人中心标签栏统计数量")
    @RequestMapping(value = "/queryCenterCountByToken", method = RequestMethod.GET)
    public ResultData queryCenterCountByToken(HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		Integer userId = user.getId();
		CenterCountAllVo result = new CenterCountAllVo();
		
		//查询我的问题总数
		int quesCount = questionsService.findQuestionsCountByMyLaunch(userId);
		
		//查询我的回答总数
		
		return ResultData.one(result);
	}
}