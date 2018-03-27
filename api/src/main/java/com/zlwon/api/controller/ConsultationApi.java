package com.zlwon.api.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.consultation.AddConsultationDto;
import com.zlwon.dto.consultation.CaseConsultationPageDto;
import com.zlwon.dto.consultation.CaseUidPageDto;
import com.zlwon.dto.consultation.ReplyConsultationDto;
import com.zlwon.rdb.entity.Consultation;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.ExhibitionCase;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ConsultationService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.ExhibitionService;
import com.zlwon.server.service.RedisService;
import com.zlwon.vo.consultation.ConsultationDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用户咨询api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/consultation")
public class ConsultationApi extends BaseApi {

	@Autowired
	private ConsultationService consultationService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ExhibitionService exhibitionService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据用户咨询ID查询用户咨询详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据用户咨询ID查询用户咨询详情")
    @RequestMapping(value = "/queryConsultationById/{id}/{entryKey}", method = RequestMethod.GET)
    public ResultData queryConsultationById(@PathVariable Integer id,@PathVariable String entryKey){
		
		//验证参数
		if(id == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据用户咨询ID查询用户咨询详情
		ConsultationDetailVo temp = consultationService.findConsultationDetailById(id);
		
		return ResultData.one(temp);
	}
	
	/**
	 * 根据openID查询用户对他的所有咨询-分页查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据工程师ID查询用户对他的所有咨询-分页查询")
    @RequestMapping(value = "/queryAllConsultationByCaseUid", method = RequestMethod.POST)
    public ResultPage queryAllConsultationByCaseUid(CaseUidPageDto form){

		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(currentPage == null || pageSize == null || StringUtils.isBlank(entryKey)){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		form.setOpenId(openId);
		
		//根据工程师ID查询工程师尚未回答过的咨询
		PageInfo<ConsultationDetailVo> pageList = consultationService.findNoAnswerConsultationByCaseUid(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * 根据openID查询该工程师回答过的咨询-分页查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据工程师ID查询该工程师回答过的咨询-分页查询")
    @RequestMapping(value = "/queryAnswerConsultationByCaseUid", method = RequestMethod.POST)
    public ResultPage queryAnswerConsultationByCaseUid(CaseUidPageDto form){

		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(currentPage == null || pageSize == null || StringUtils.isBlank(entryKey)){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		form.setOpenId(openId);
		
		//根据工程师ID查询用户对他的所有咨询
		PageInfo<ConsultationDetailVo> pageList = consultationService.findAnswerConsultationByCaseUid(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * 用户新增咨询信息
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "用户新增咨询信息")
    @RequestMapping(value = "/addConsultationByUser", method = RequestMethod.POST)
    public ResultData addConsultationByUser(AddConsultationDto form){

		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String entryKey = form.getEntryKey();  //微信加密字符串
		Integer cid = form.getCid();  //展会案例ID
		String title = form.getTitle();  //咨询标题
		String content = form.getContent();  //咨询详情
		String contentVoice = form.getContentVoice();  //语音内容
		
		if(StringUtils.isBlank(entryKey) || cid == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据openID查询用户ID
		Customer user = customerService.selectCustomerByOpenId(openId);
		if(user == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		//根据展会案例ID查询工程师ID
		ExhibitionCase mycase = exhibitionService.selectExhibitionCaseById(cid);
		if(mycase == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//新增用户咨询信息
		Consultation addInfo = new Consultation();
		addInfo.setContent(content);
		addInfo.setContentVoice(contentVoice);
		addInfo.setCreateTime(new Date());
		addInfo.setCid(cid);
		addInfo.setReplyCont(null);
		addInfo.setReplyContVoice(null);
		addInfo.setReplyTime(null);
		addInfo.setSid(mycase.getUid());
		addInfo.setTitle(title);
		addInfo.setUid(user.getId());
		
		Consultation temp = consultationService.insertConsultation(addInfo);
		
		return ResultData.one(temp);
	}
	
	/**
	 * 工程师回复咨询信息
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "工程师回复咨询信息")
    @RequestMapping(value = "/replyConsultation", method = RequestMethod.POST)
    public ResultData replyConsultation(ReplyConsultationDto form){

		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer consultationId = form.getConsultationId();  //用户咨询ID
		String replyCont = form.getReplyCont();  //回复内容
		String replyContVoice = form.getReplyContVoice();  //语音回复内容
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(consultationId == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		Consultation updateInfo = new Consultation();
		updateInfo.setId(consultationId);
		updateInfo.setReplyCont(replyCont);
		updateInfo.setReplyContVoice(replyContVoice);
		updateInfo.setReplyTime(new Date());
		
		int count = consultationService.updateConsultationReply(updateInfo);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 根据案例ID查询跟该案例有关的用户咨询-分页查询
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "根据案例ID和用户查询跟该案例有关的用户咨询-分页查询")
    @RequestMapping(value = "/queryConsultationByCasePage", method = RequestMethod.POST)
    public ResultPage queryConsultationByCaseId(CaseConsultationPageDto form){

		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer caseId = form.getCaseId();  //案例ID
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(currentPage == null || pageSize == null || caseId == null || StringUtils.isBlank(entryKey)){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		form.setOpenId(openId);
		
		//根据案例ID查询跟该案例有关的用户咨询
		PageInfo<ConsultationDetailVo> pageList = consultationService.findConsultationByCaseIdList(form);
		
		return ResultPage.list(pageList);
	}
}
