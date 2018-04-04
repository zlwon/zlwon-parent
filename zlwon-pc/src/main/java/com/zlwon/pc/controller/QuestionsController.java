package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.questions.InsertQuestionsDto;
import com.zlwon.dto.pc.specification.PcSearchSpecPageDto;
import com.zlwon.rdb.entity.Customer;
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
	
	@ApiOperation(value = "pc端分页查询物性表信息")
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
		
		/*Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		String manufacturerStr = form.getManufacturerStr();  //生产商字符串
		String brandNameStr = form.getBrandNameStr();  //商标字符串
		String baseMaterialStr = form.getBaseMaterialStr();  //基材字符串
		String searchText = form.getSearchText();  //填写搜索栏字符串
		
		if(currentPage == null || pageSize == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}*/
		
		
		return ResultData.ok();
	}
}
