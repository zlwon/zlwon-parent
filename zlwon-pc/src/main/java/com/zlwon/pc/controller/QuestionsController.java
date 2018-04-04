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
import com.zlwon.dto.pc.questions.InsertQuestionsDto;
import com.zlwon.dto.pc.specification.PcSearchSpecPageDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.vo.specification.SpecificationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 提问表pc端接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/questions")
public class QuestionsController extends BaseController {

	@Autowired
	private QuestionsService questionsService;
	
	/**
	 * pc端新增提问
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端新增提问")
    @RequestMapping(value = "/insertQuestions", method = RequestMethod.POST)
    public ResultData insertQuestions(InsertQuestionsDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getParameter("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		String title = form.getTitle();  //提问标题
		String content = form.getContent();  //问题内容

		if(infoId == null || infoClass == null || StringUtils.isBlank(title) || StringUtils.isBlank(content)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Questions record = new Questions();
		record.setIid(infoId);
		record.setInfoClass(infoClass);
		record.setNsid(null);
		record.setTitle(title);
		record.setContent(content);
		record.setCreateTime(new Date());
		record.setExamine(1);
		record.setUid(user.getId());
		
		int count = questionsService.insertQuestions(record);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
}
