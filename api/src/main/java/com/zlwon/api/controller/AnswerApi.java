package com.zlwon.api.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.api.answer.InsertAnswerWCDto;
import com.zlwon.dto.api.answer.OperateAnswerRecordDto;
import com.zlwon.dto.api.answer.QueryAnswerByQuestionIdWCDto;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.rdb.entity.AnswerRecord;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.AnswerRecordService;
import com.zlwon.server.service.AnswerService;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.vo.pc.answer.AnswerDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 小程序端回答接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/answer")
public class AnswerApi extends BaseApi {

	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuestionsService questionsService;
	
	@Autowired
	private AnswerRecordService answerRecordService;
	
	/**
	 * 小程序端新增回答
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "小程序端新增回答")
    @RequestMapping(value = "/insertAnswer", method = RequestMethod.POST)
	public ResultData insertAnswer(InsertAnswerWCDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
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
		
		Questions quesInfo = questionsService.findQuestionsById(questionId);
		if(quesInfo == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
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
	 * 新增/删除回答点赞记录
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增/删除回答点赞记录")
    @RequestMapping(value = "/operateAnswerRecord", method = RequestMethod.POST)
	public ResultData operateAnswerRecord(OperateAnswerRecordDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer answerId = form.getAnswerId();  //回答ID
		
		if(answerId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer userId = user.getId();  //用户ID
		Integer status = 0;  //0：取消点赞成功，1：点赞成功
		
		//根据用户ID和回答ID查询点赞记录
		AnswerRecord valid = answerRecordService.findAnswerRecordByUserAnswer(userId,answerId);
		if(valid == null){  //用户未点赞，执行点赞
			AnswerRecord newRecord = new AnswerRecord();
			newRecord.setUid(userId);
			newRecord.setAnswerId(answerId);
			newRecord.setCreateTime(new Date());
			
			int count = answerRecordService.insertAnswerRecord(newRecord);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			status = 1;
		}else{  //用户已点赞，取消点赞
			int count = answerRecordService.deleteAnswerRecordById(valid.getId());
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			status = 0;
		}
		
		return ResultData.one(status);
	}
	
	/**
	 * 根据问题ID查询分页回答
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据问题ID查询分页回答")
    @RequestMapping(value = "/queryAnswerByQuestionId", method = RequestMethod.POST)
    public ResultPage queryAnswerByQuestionId(QueryAnswerByQuestionIdWCDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
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
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
		if(user == null){
			form.setUserId(null);
		}else{
			form.setUserId(user.getId());
		}
		
		//根据问题ID查询回答
		PageInfo<AnswerDetailVo> pageList = answerService.findWCAnswerByquestionId(form);
		
		return ResultPage.list(pageList);
	}
}
