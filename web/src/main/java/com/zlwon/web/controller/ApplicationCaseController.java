package com.zlwon.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.applicationCase.ApplicationCaseDto;
import com.zlwon.pojo.ApplicationCaseMessage;
import com.zlwon.pojo.SpecificationMessage;
import com.zlwon.pojo.constant.MessageConstant;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.utils.JsonUtils;
import com.zlwon.vo.applicationCase.ApplicationCaseListVo;
import com.zlwon.vo.applicationCase.ApplicationCaseVo;
import com.zlwon.web.annotations.AuthLogin;

import io.swagger.annotations.Api;

/**
 * 案例管理api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/case")
@AuthLogin
public class ApplicationCaseController {

	@Autowired
	private ApplicationCaseService applicationCaseService;
	
	@Autowired
	private  KafkaTemplate<String, String>  kafkaTemplate;
	@Value("${kafka.topic.add.applicationCase}")
	private  String    addAplicationCase;
	
	/**
	 * 得到所有案例,案例名称模糊查询
	 * @param pageIndex
	 * @param pageSize
	 * @param key 关键字，案例名称模糊查询
	 * @return
	 */
	@RequestMapping(value="queryAllApplicateCase",method=RequestMethod.GET)
	public   ResultPage   queryAllApplicateCase(@RequestParam(defaultValue="${page.pageIndex}")Integer  pageIndex,
			@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize,String  key){
		PageInfo<ApplicationCaseListVo> info = applicationCaseService.findAllApplicationCase(pageIndex,pageSize,key);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 根据案例id，得到案例详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryApplicateCaseById",method=RequestMethod.GET)
	public   ResultData   queryApplicateCaseById(Integer  id){
		ApplicationCaseVo applicationCase = applicationCaseService.findAppCaseDetailsById(id);
		if(applicationCase==null  || applicationCase.getDel()!=1){
			return  ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		return  ResultData.one(applicationCase);
	}
	
	/**
	 * 根据案例id，保存修改后的案例信息
	 * @param applicationCase
	 * @return
	 */
	@RequestMapping(value="editApplicateCaseById",method=RequestMethod.POST)
	public  ResultData   editApplicateCaseById(ApplicationCaseDto  applicationCase){
		applicationCaseService.alterApplicateCaseById(applicationCase);
		sendMessageByApplicationCaseAddOrUpdate(applicationCase.getId());
		return  ResultData.ok();
	}
	
	/**
	 * 根据案例id，删除指定的案例
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelApplicateCaseById",method=RequestMethod.GET)
	public  ResultData  cancelApplicateCaseById(Integer  id){
		applicationCaseService.removeApplicateCaseById(id);
		return  ResultData.ok();
	}
	
	/**
	 * 添加案例信息
	 * @param applicationCase
	 * @return
	 */
	@RequestMapping(value="addApplicateCase",method=RequestMethod.POST)
	public  ResultData  addApplicateCase(HttpServletRequest  request,ApplicationCaseDto  applicationCase){
		long id = applicationCaseService.saveApplicateCase(request,applicationCase,1);
		sendMessageByApplicationCaseAddOrUpdate((int)id);
		return ResultData.ok();
	}
	
	/**
	 * 设置案例为热门
	 * 注意：要清除缓存
	 * @param id
	 * @return
	 */
	@RequestMapping(value="editApplicationCaseHot",method=RequestMethod.GET)
	public  ResultData  editApplicationCaseHot(Integer   id){
		//设置案例为热门，需要判断热门个数是否少于5个，
		applicationCaseService.alterApplicationCaseHot(id);
		return ResultData.ok();
	}
	
	/**
	 * 取消热门案例
	 * 注意：要清除缓存
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelHotApplicationCase",method=RequestMethod.GET)
	public  ResultData  cancelHotApplicationCase(Integer   id){
		//设置案例为非热门
		applicationCaseService.removeHotApplicationCase(id);
		return ResultData.ok();
	}
	
	/**
	 * 案例添加或修改时，发送消息到mq，
	 * @param id
	 */
	private  void   sendMessageByApplicationCaseAddOrUpdate(Integer  id){
		ApplicationCaseMessage applicationCaseMessage = new ApplicationCaseMessage(id, MessageConstant.ADDORUPDATE_APPLICATIONCASE_TYPE);
		kafkaTemplate.send(addAplicationCase, JsonUtils.objectToJson(applicationCaseMessage));
	}
}
