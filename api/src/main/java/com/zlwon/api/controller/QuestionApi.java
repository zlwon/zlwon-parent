package com.zlwon.api.controller;

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
import com.zlwon.constant.IntegrationDeatilCode;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.api.question.InsertQuestionsWCDto;
import com.zlwon.dto.api.question.QueryDefineClearQuestionsDto;
import com.zlwon.dto.api.question.QueryQuestionListByInfoIdDto;
import com.zlwon.dto.pc.answer.QueryInvitateAnswerUsersDto;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.rdb.entity.Questions;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.AnswerService;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.InformService;
import com.zlwon.server.service.IntegrationDeatilMapService;
import com.zlwon.server.service.MailService;
import com.zlwon.server.service.QuestionsService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.pc.answer.InvitateAnswerDetailVo;
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
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SpecificationService specificationService;
	
	@Autowired
	private ApplicationCaseService applicationCaseService;
	
	@Autowired
	private InformService informService;
	
	@Autowired
	private IntegrationDeatilMapService integrationDeatilMapService;
	
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
	
	/**
	 * 根据信息ID查询提问列表
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据信息ID查询提问列表")
    @RequestMapping(value = "/queryQuestionListByInfoId", method = RequestMethod.POST)
	public ResultData queryQuestionListByInfoId(QueryQuestionListByInfoIdDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer infoId = form.getInfoId();  //信息ID
		Integer infoClass = form.getInfoClass();  //信息类别：1:物性、2:案例
		
		if(infoId == null || infoClass == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
		if(user == null){  //登录
			form.setUserId(null);
		}else{  //未登录
			form.setUserId(user.getId());
		}
		
		List<QuestionsDetailVo> resut = questionsService.findQuestionsLsitByInfoId(form);
		
		return ResultData.one(resut);
	}
	
	/**
	 * 小程序端新增提问
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "小程序端新增提问")
    @RequestMapping(value = "/insertQuestions", method = RequestMethod.POST)
	public ResultData insertQuestions(InsertQuestionsWCDto form,HttpServletRequest request){
		
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
		if(StringUtils.isNotBlank(inviteUser)){
			
			//验证用户积分是否足够
			if(user.getIntegration() < Math.abs(IntegrationDeatilCode.INVITATE_ANSWER.getNum())){
				return ResultData.error(StatusCode.USER_INTEGRATION_NOT_ENOUGH);
			}
			
			String[] arrUser = inviteUser.split(",");
			int arrLength = arrUser.length;  //数组长度
			if(arrLength>3){
				return ResultData.error(StatusCode.UP_USERS_LIMIT);
			}
		}
		
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
		if(StringUtils.isNotBlank(inviteUser)){  //如果邀请用户不为空
			Thread t3 = new Thread(new Runnable() {
				@Override
				public void run() {
					String quesNickName = user.getNickname();  //提问者昵称
					
					//根据用户ID拼接字符串查询用户信息
					List<Customer> userList = customerService.findCustomerByidStr(inviteUser);
					
					//查询信息来源
					String source = "";
					if(infoClass == 1){  //物性
						Specification myspec = specificationService.findSpecificationById(infoId);
						source = myspec.getName();
					}else{
						ApplicationCase myCase = applicationCaseService.findAppCaseById(infoId);
						source = myCase.getTitle();
					}
					
					//给提问者减少积分
					int lessCount = customerService.updateIntegrationByUid(user.getId(), IntegrationDeatilCode.INVITATE_ANSWER.getNum());
					
					IntegrationDeatilMap lessInterDeatil = new IntegrationDeatilMap();
					lessInterDeatil.setType(IntegrationDeatilCode.INVITATE_ANSWER.getCode());
					lessInterDeatil.setDescription(IntegrationDeatilCode.INVITATE_ANSWER.getMessage());
					lessInterDeatil.setIntegrationNum(IntegrationDeatilCode.INVITATE_ANSWER.getNum());
					lessInterDeatil.setChangeType(0);
					lessInterDeatil.setUid(user.getId());
					lessInterDeatil.setCreateTime(new Date());
					
					int igLessCount = integrationDeatilMapService.insertIntegrationDeatilMap(lessInterDeatil);
					
					if(userList != null && userList.size() > 0){
						for(Customer temp : userList){
							Map<String, Object> model = new HashMap<String, Object>();
					        model.put("nickname", temp.getNickname());  //被邀请者昵称
					        model.put("headerimg", temp.getHeaderimg());  //被邀请者头像
					        model.put("quesNickName", quesNickName);  //邀请者昵称
					        model.put("title", title);  //问题
					        model.put("source", source);  //来源
					        if(infoClass == 1){  //物性
					        	model.put("pageUrl", "http://www.zlwon.com/page/space/detail.html?id="+infoId);
					        }else{
					        	model.put("pageUrl", "http://www.zlwon.com/page/case/detail.html?id="+infoId);
					        }
							
							if(StringUtils.isNotBlank(temp.getEmail())){
								mailService.sendVelocityTemplateMail(temp.getEmail(), quesNickName+"邀请您回答", "invitateEmail.vm",model);
							}
							
							//添加通知消息消息
							Inform recordInfo = new Inform();
							recordInfo.setCreateTime(new Date());
							recordInfo.setIid(record.getId());
							recordInfo.setUid(temp.getId());
							recordInfo.setReadStatus((byte) 0);
							recordInfo.setStatus((byte) 1);
							recordInfo.setType((byte) 8);
							
							int informCount = informService.insertInform(recordInfo);
							
							//给被提问者增加积分
							int addCount = customerService.updateIntegrationByUid(user.getId(), IntegrationDeatilCode.PASSIVE_INVITATE_ANSWER.getNum());
							
							IntegrationDeatilMap addInterDeatil = new IntegrationDeatilMap();
							addInterDeatil.setType(IntegrationDeatilCode.PASSIVE_INVITATE_ANSWER.getCode());
							addInterDeatil.setDescription(IntegrationDeatilCode.PASSIVE_INVITATE_ANSWER.getMessage());
							addInterDeatil.setIntegrationNum(IntegrationDeatilCode.PASSIVE_INVITATE_ANSWER.getNum());
							addInterDeatil.setChangeType(1);
							addInterDeatil.setUid(temp.getId());
							addInterDeatil.setCreateTime(new Date());
							
							int igAddCount = integrationDeatilMapService.insertIntegrationDeatilMap(addInterDeatil);
						}
					}
					
				}
			});
			
			//启用线程
			t3.start();
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 根据提问ID查询提问详情
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据提问ID查询提问详情")
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
		Customer user = getRedisLoginCustomer(token);
		//根据问题ID查询问题详情
		if(user == null){
			quesInfo = questionsService.findSingleQuestionDetailById(id,null);
		}else{
			quesInfo = questionsService.findSingleQuestionDetailById(id,user.getId());
		}
		
		return ResultData.one(quesInfo);
	}
	
	/**
	 * 分页查询邀请回答推荐用户
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分页查询邀请回答推荐用户")
    @RequestMapping(value = "/queryInvitateAnswerUsers", method = RequestMethod.POST)
    public ResultPage queryInvitateAnswerUsers(QueryInvitateAnswerUsersDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer infoId = form.getInfoId();  //信息ID
		Integer type = form.getType();  //类型 1：物性 2：案例
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(infoId == null || type == null || currentPage == null || pageSize == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//查询邀请回答推荐用户
		PageInfo<InvitateAnswerDetailVo> pageList = answerService.findInvitateAnswerUserPage(form);
		
		return ResultPage.list(pageList);
	}
}
