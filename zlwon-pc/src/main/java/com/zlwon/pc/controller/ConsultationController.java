package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.consultation.QueryConsultationMePageDto;
import com.zlwon.dto.pc.consultation.QueryMyConsultationPageDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ConsultationService;
import com.zlwon.server.service.RedisService;
import com.zlwon.vo.pc.consultation.PcConsultationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 咨询pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/consultation")
public class ConsultationController extends BaseController {

	@Autowired
	private ConsultationService consultationService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * pc端查询我提出的所有咨询
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端查询我提出的所有咨询")
    @RequestMapping(value = "/queryMyConsultationPage", method = RequestMethod.POST)
    public ResultPage queryMyConsultationPage(QueryMyConsultationPageDto form,HttpServletRequest request){
		
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

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我提出的所有咨询
		PageInfo<PcConsultationDetailVo> pageList = consultationService.findMyConsultationPage(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端查询咨询我的所有咨询
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端查询咨询我的所有咨询")
    @RequestMapping(value = "/queryConsultationMePage", method = RequestMethod.POST)
    public ResultPage queryConsultationMePage(QueryConsultationMePageDto form,HttpServletRequest request){
		
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

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询咨询我的所有咨询
		PageInfo<PcConsultationDetailVo> pageList = consultationService.findConsultationMePage(form);
		
		return ResultPage.list(pageList);
	}
	
}
