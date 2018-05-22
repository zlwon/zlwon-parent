package com.zlwon.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.api.question.QueryDefineClearQuestionsDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.vo.pc.questions.QuestionsDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 小程序端提问接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/question")
public class QuestionApi extends BaseApi  {

	@Autowired
	private QuestionsService questionsService;
	
	/**
	 * 分页查询特定类型的问题
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分页查询特定类型的问题")
    @RequestMapping(value = "/queryDefineClearQuestions", method = RequestMethod.POST)
	public ResultPage queryDefineClearQuestions(QueryDefineClearQuestionsDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		Integer moduleType = form.getModuleType();  //模块类型
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
		if(user == null){  //登录
			form.setUserId(null);
		}else{  //未登录
			form.setUserId(user.getId());
		}
		
		//分页查询特定类型的问题
		PageInfo<QuestionsDetailVo> pageList = questionsService.findWCSpecifyQuestions(form);
		
		return ResultPage.list(pageList);
	}
}
