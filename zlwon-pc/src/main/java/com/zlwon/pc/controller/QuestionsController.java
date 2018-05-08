package com.zlwon.pc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.questions.InsertQuestionsDto;
import com.zlwon.dto.pc.questions.QueryAllSpecifyQuestionsDto;
import com.zlwon.dto.pc.questions.QueryAttentionMeQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAnswerQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyAttentionQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyCollectQuestionsDto;
import com.zlwon.dto.pc.questions.QueryMyLaunchQuestionsDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.rdb.entity.User;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.MailService;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseQuestionAndAnswerVo;
import com.zlwon.vo.pc.questions.QuestionsDetailVo;

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
	
	@Autowired
	private MailService mailService;
	
	/**
	 * pc端新增提问
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端新增提问")
    @RequestMapping(value = "/insertQuestions", method = RequestMethod.POST)
    public ResultData insertQuestions(InsertQuestionsDto form,HttpServletRequest request){
		
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
		
		Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		Integer moduleType = form.getModuleType();  //模块类型
		String title = form.getTitle();  //提问标题
		String content = form.getContent();  //问题内容
		String inviteUser = form.getInviteUser();  //邀请用户（最多三个，可不传）

		if(infoId == null || infoClass == null || StringUtils.isBlank(title)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//如果邀请用户不为空,判断用户人数
		/*if(StringUtils.isNotBlank(inviteUser)){  
			String[] arrUser = inviteUser.split(",");
			int arrLength = arrUser.length;  //数组长度
			if(arrLength>3){
				return ResultData.error(StatusCode.UP_USERS_LIMIT);
			}
		}*/
		
		Questions record = new Questions();
		record.setIid(infoId);
		record.setInfoClass(infoClass);
		record.setModuleType(moduleType);
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
		
		//开启线程处理邮件发送问题
		/*if(StringUtils.isNotBlank(inviteUser)){  //如果邀请用户不为空
			Thread t3 = new Thread(new Runnable() {
				@Override
				public void run() {
					//根据用户ID拼接字符串查询用户信息
					List<Customer> userList = null;
					
					if(userList != null && userList.size() > 0){
						Map<String, Object> model = new HashMap<String, Object>();
				        model.put("user", "871271816@qq.com");
				        model.put("specName", "VS50");
						
						for(Customer temp : userList){
							if(StringUtils.isNotBlank(temp.getEmail())){
								mailService.sendVelocityTemplateMail(temp.getEmail(), "邀请您回答", "specPdf.vm",model);
							}
						}
					}
					
				}
			});
			
			//启用线程
			t3.start();
		}*/
		
		return ResultData.ok();
	}
	
	/**
	 * pc端根据提问ID查询提问极其回答详情
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端根据提问ID查询提问详情")
    @RequestMapping(value = "/queryQuestionDetailById", method = RequestMethod.GET)
    public ResultData queryQuestionDetailById(@RequestParam Integer id,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		QuestionsDetailVo quesInfo = null;
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		//根据问题ID查询问题详情
		if(user == null){
			quesInfo = questionsService.findSingleQuestionDetailById(id,null);
		}else{
			quesInfo = questionsService.findSingleQuestionDetailById(id,user.getId());
		}
		
		return ResultData.one(quesInfo);
	}
	
	/**
	 * pc端我发起的提问
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端我发起的提问(特定)")
    @RequestMapping(value = "/queryMyLaunchQuestions", method = RequestMethod.POST)
    public ResultPage queryMyLaunchQuestions(QueryMyLaunchQuestionsDto form,HttpServletRequest request){
		
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
		
		//Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我的提问问题
		PageInfo<QuestionsDetailVo> pageList = questionsService.findQuestionsByMyLaunch(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端我收藏的问题
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端我收藏的问题")
    @RequestMapping(value = "/queryMyCollectQuestions", method = RequestMethod.POST)
    public ResultPage queryMyCollectQuestions(QueryMyCollectQuestionsDto form,HttpServletRequest request){
		
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
		
		//Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我收藏的问题
		PageInfo<QuestionsDetailVo> pageList = questionsService.findQuestionsByMyCollect(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端我回答的问题
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端我回答的问题")
    @RequestMapping(value = "/queryMyAnswerQuestions", method = RequestMethod.POST)
    public ResultPage queryMyAnswerQuestions(QueryMyAnswerQuestionsDto form,HttpServletRequest request){
		
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
		
		//Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我回答的问题
		PageInfo<QuestionsDetailVo> pageList = questionsService.findQuestionsByMyAnswer(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端我关注的人的问题
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端我关注的人的问题")
    @RequestMapping(value = "/queryMyAttentionQuestions", method = RequestMethod.POST)
    public ResultPage queryMyAttentionQuestions(QueryMyAttentionQuestionsDto form,HttpServletRequest request){
		
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
		
		//Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我回答的问题
		PageInfo<QuestionsDetailVo> pageList = questionsService.findMyAttentionQuestions(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端关注我的人的问题
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端关注我的人的问题")
    @RequestMapping(value = "/queryAttentionMeQuestions", method = RequestMethod.POST)
    public ResultPage queryAttentionMeQuestions(QueryAttentionMeQuestionsDto form,HttpServletRequest request){
		
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
		
		//Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我回答的问题
		PageInfo<QuestionsDetailVo> pageList = questionsService.findAttentionMeQuestions(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端查询特定类型的问题（可指定具体）
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端查询特定类型的问题（可指定具体）")
    @RequestMapping(value = "/queryAllSpecifyQuestions", method = RequestMethod.POST)
    public ResultPage queryAllSpecifyQuestions(QueryAllSpecifyQuestionsDto form,HttpServletRequest request){
		
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
		Customer user = accessCustomerByToken(token);
		if(user == null){  //登录
			form.setUserId(null);
		}else{  //未登录
			form.setUserId(user.getId());
		}
		
		//分页查询特定类型的问题
		PageInfo<QuestionsDetailVo> pageList = questionsService.findAllSpecifyQuestions(form);
		
		return ResultPage.list(pageList);
	}
	
	
	
	
	/**
	 * 得到首页最热门的问答(根据提问回答最多查询，最多4个)
	 * @return
	 */
	@RequestMapping(value = "/queryHotQuestions", method = RequestMethod.GET)
	public   ResultData  queryHotQuestions(){
		List<IndexHotApplicationCaseQuestionAndAnswerVo>  list = questionsService.findHotQuestions();
		return   ResultData.one(list);
	}
}
