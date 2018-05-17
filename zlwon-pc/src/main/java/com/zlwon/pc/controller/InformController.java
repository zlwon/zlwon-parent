package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.InformService;

/**
 * 用户消息api
 * @author yuand
 *
 */
@RestController
@RequestMapping("/pc/inform")
public class InformController {

	@Autowired
	private  InformService   informService;
	
	/**
	 * 得到用户未读消息总个数
	 * @param request
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="queryBadgeNumber",method=RequestMethod.GET)
	public  ResultData  queryBadgeNumber(HttpServletRequest request){
		int num = informService.findBadgeNumber(request);
		return  ResultData.one(num);
	}
	
	/**
	 * 得到用户所有消息，根据类型查找-分页查找
	 * @param type 0查所有1用户提问审核2用户回答审核3案例编辑审核4用户新增物性标签5材料报价单6用户认证
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="queryAllInform",method=RequestMethod.GET)
	public  ResultPage  queryAllInform(HttpServletRequest request,Integer   type,@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="10")Integer  pageSize){
		PageInfo  info = informService.findAllInform(request,pageIndex,pageSize,type);
		return  ResultPage.list(info);
	}
	
	/**
	 * 设置消息已读
	 * @param ids 消息id数组
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="informMakeRead",method=RequestMethod.POST)
	public  ResultData  informMakeRead(HttpServletRequest request,String[]  ids){
		informService.alterInformMakeReadByIds(request,ids);
		return  ResultData.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
