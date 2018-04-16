package com.zlwon.pc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.answer.InsertAnswerDto;
import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.AnswerService;
import com.zlwon.vo.pc.answer.AnswerDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 提问回答表pc端接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/answer")
public class AnswerController extends BaseController {

	@Autowired
	private AnswerService answerService;
	
	/**
	 * pc端新增提问回答
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端新增提问回答")
    @RequestMapping(value = "/insertAnswer", method = RequestMethod.POST)
    public ResultData insertAnswer(InsertAnswerDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer questionId = form.getQuestionId();  //问题ID
		String content = form.getContent();  //回答内容

		if(questionId == null || StringUtils.isBlank(content)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Answer record = new Answer();
		record.setQid(questionId);
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setExamine(1);
		record.setUid(user.getId());
		
		int count = answerService.insertAnswer(record);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * pc端根据问题ID查询回答
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端根据问题ID查询回答")
    @RequestMapping(value = "/queryAnswerByQuestionId", method = RequestMethod.POST)
    public ResultPage queryAnswerByQuestionId(QueryAnswerByQuestionIdDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer questionId = form.getQuestionId();  //问题ID

		if(currentPage == null || pageSize == null || questionId == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//根据问题ID查询回答
		PageInfo<AnswerDetailVo> pageList = answerService.findAnswerByquestionId(form);
		
		return ResultPage.list(pageList);
	}
}
