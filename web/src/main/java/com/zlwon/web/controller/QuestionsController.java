package com.zlwon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 提问api
 * @author yuand
 *
 */
@AuthLogin
@RestController
@RequestMapping("questions")
public class QuestionsController {
	
	@Autowired
	private  QuestionsService   questionsService;

	/**
	 * 得到所有提问信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="queryAllQuestions",method=RequestMethod.GET)
	public   ResultPage   queryAllQuestions(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize){
		PageInfo  info = questionsService.findAllQuestionsPage(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	/**
	 * 设置提问信息为驳回
	 * @param id 提问id
	 * @param content 驳回内容
	 * @return
	 */
	@RequestMapping(value="editQuestionsFailed",method=RequestMethod.POST)
	public   ResultData   editQuestionsFailed(Integer  id,String  content){
		questionsService.alterQuestionsFailed(id,content);
		return  ResultData.ok();
	}
	
	/**
	 * 得到提问驳回信息
	 * @param id 提问id
	 * @return
	 */
	@RequestMapping(value="queryQuestionsFailedContent",method=RequestMethod.GET)
	public  ResultData   queryQuestionsFailedContent(Integer  id){
		String   content = questionsService.findQuestionsFailedContent(id);
		return  ResultData.one(content);
	}
	
	/**
	 * 管理员发送邀请问答邮件
	 * @param uids 被邀请的用户id，多个逗号隔开
	 * @param id 问题id
	 * @return
	 */
	@RequestMapping(value="managerSendAnInvitationEmail",method=RequestMethod.GET)
	public  ResultData  managerSendAnInvitationEmail(String   uids,Integer  id){
		questionsService.sendAnInvitationEmail(uids,id);
		return  ResultData.ok();
	}
	
	
	
	
	
	/**
	 * 设置提问信息为通过,后台无法设置通过，只能驳回
	 * @param id 提问id
	 * @return
	 */
/*	@RequestMapping(value="editQuestionsSuccess",method=RequestMethod.POST)
	public   ResultData   editQuestionsSuccess(Integer  id){
		questionsService.alterQuestionsSuccess(id);
		return  ResultData.ok();
	}*/
}
