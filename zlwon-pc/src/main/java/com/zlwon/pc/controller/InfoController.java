package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.info.QueryPcInfoByPageDto;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.InfoService;
import com.zlwon.vo.pc.info.InfoDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 资讯pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/info")
public class InfoController extends BaseController {

	@Autowired
	private InfoService infoService;
	
	/**
	 * 据资讯ID查询资讯信息
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "据资讯ID查询资讯信息")
    @RequestMapping(value = "/queryInfoById", method = RequestMethod.GET)
	public ResultData queryInfoById(@RequestParam Integer id,HttpServletRequest request){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		InfoDetailVo result = infoService.findPcInfoById(id);
		
		return ResultData.one(result);
	}
	
	/**
	 * 分页查询资讯信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分页查询资讯信息")
    @RequestMapping(value = "/queryInfoByPage", method = RequestMethod.POST)
	public ResultPage queryInfoByPage(QueryPcInfoByPageDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//分页查询资讯
		PageInfo<InfoDetailVo> pageList = infoService.findPcInfoByPageList(form);
		
		return ResultPage.list(pageList);
	}
}
