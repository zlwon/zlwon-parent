package com.zlwon.pc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.answer.InsertAnswerDto;
import com.zlwon.dto.pc.answer.InsertAnswerRecordDto;
import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.dto.pc.answer.QueryMyAnswerByCenterPage;
import com.zlwon.dto.pc.answer.UpdateAnswerPcDto;
import com.zlwon.pc.annotations.AuthLogin;
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
import com.zlwon.vo.pc.answer.AnswerQuestionDetailVo;

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
	
	@Autowired
	private QuestionsService questionsService;
	
	@Autowired
	private AnswerRecordService answerRecordService;
	
	/**
	 * pc端新增提问回答
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
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
	 * pc端根据问题ID查询回答
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端根据问题ID查询回答")
    @RequestMapping(value = "/queryAnswerByQuestionId", method = RequestMethod.POST)
    public ResultPage queryAnswerByQuestionId(QueryAnswerByQuestionIdDto form,HttpServletRequest request){
		
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
		Customer user = accessCustomerByToken(token);
		if(user == null){
			form.setUserId(null);
		}else{
			form.setUserId(user.getId());
		}
		
		//根据问题ID查询回答
		PageInfo<AnswerDetailVo> pageList = answerService.findAnswerByquestionId(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端分页查询我的回答-个人中心
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端分页查询我的回答-个人中心")
    @RequestMapping(value = "/queryMyAnswerByCenterPage", method = RequestMethod.POST)
    public ResultPage queryMyAnswerByCenterPage(QueryMyAnswerByCenterPage form,HttpServletRequest request){
		
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
		
		//分页查询我的回答
		form.setUserId(user.getId());
		PageInfo<AnswerQuestionDetailVo> pageList = answerService.findMyAnswerByCenterPage(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端新增/删除回答点赞记录
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端新增/删除回答点赞记录")
    @RequestMapping(value = "/insertAnswerRecord", method = RequestMethod.POST)
    public ResultData insertAnswerRecord(InsertAnswerRecordDto form,HttpServletRequest request){
		
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
	 * pc端删除回答
	 * @param answerId
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端删除回答")
    @RequestMapping(value = "/deleteAnswer", method = RequestMethod.GET)
    public ResultData deleteAnswer(@RequestParam Integer answerId,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(answerId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer userId = user.getId();  //用户ID
	
		//根据回答ID查询回答信息
		Answer myAnswer = answerService.findAnswerById(answerId);
		if(myAnswer == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//判断是否是当前用户回答
		if(myAnswer.getUid() == user.getId()){ 
			//删除该回答
			int count = answerService.deleteAnswer(answerId);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
		}else{
			return ResultData.error(StatusCode.USER_NOT_PERMIT);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * pc端修改回答信息
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端修改回答信息")
    @RequestMapping(value = "/updateAnswer", method = RequestMethod.POST)
    public ResultData updateAnswer(UpdateAnswerPcDto form,HttpServletRequest request){
		
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
		
		Integer answerId = form.getAnswerId();  //回答ID
		String content = form.getContent();  //回答内容
		
		if(answerId == null || StringUtils.isBlank(content) ){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer userId = user.getId();  //用户ID
		
		//根据回答ID查询回答信息
		Answer myAnswer = answerService.findAnswerById(answerId);
		if(myAnswer == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//判断是否是当前用户回答
		if(myAnswer.getUid() == user.getId()){ 
			
			myAnswer.setContent(content);
			
			//修改该回答
			int count = answerService.updateAnswer(myAnswer);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
		}else{
			return ResultData.error(StatusCode.USER_NOT_PERMIT);
		}
		
		return ResultData.ok();
	}
}
