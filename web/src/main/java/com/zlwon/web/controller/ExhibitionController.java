package com.zlwon.web.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.Exhibition;
import com.zlwon.rdb.entity.ExhibitionCase;
import com.zlwon.rdb.entity.ExhibitionCaseMap;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ExhibitionService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 展会管理api
 * 
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/exhibition")
@AuthLogin
public class ExhibitionController {

	@Autowired
	private ExhibitionService exhibitionService;

	/**
	 * 得到所有展会
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryAllExhibition", method = RequestMethod.GET)
	public ResultPage queryAllExhibition(@RequestParam(defaultValue = "1") Integer pageIndex,
			@RequestParam(defaultValue = "30") Integer pageSize) {
		PageInfo<Exhibition> info = exhibitionService.findAllExhibitionPage(pageIndex, pageSize);
		return ResultPage.list(info);
	}

	/**
	 * 添加展会
	 * 
	 * @param exhibition
	 * @return
	 */
	@RequestMapping(value = "addExhibition", method = RequestMethod.POST)
	public ResultData addExhibition(Exhibition exhibition) {
		System.out.println(exhibition.getName());
		exhibitionService.saveExhibition(exhibition);
		return ResultData.ok();
	}

	/**
	 * 根据展会id，得到展会信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryExhibitionById", method = RequestMethod.GET)
	public ResultData queryExhibitionById(Integer id) {
		Exhibition exhibition = exhibitionService.findExhibitionInfoById(id);
		// 判断是否存在
		if (exhibition == null || exhibition.getDel() != 1) {
			// 数据不存在
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		return ResultData.one(exhibition);
	}

	/**
	 * 保存编辑后的展会信息（只更新展会普通信息，案例不做修改）
	 * 
	 * @param exhibition
	 * @return
	 */
	@RequestMapping(value = "editExhibition", method = RequestMethod.POST)
	public ResultData editExhibition(Exhibition exhibition) {
		exhibitionService.alterExhibitionSelective(exhibition);
		return ResultData.ok();
	}

	/**
	 * 根据展会id，删除展会信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancelExhibitionById", method = RequestMethod.GET)
	public ResultData cancelExhibitionById(Integer id) {
		exhibitionService.removeExhibitionById(id);
		return ResultData.ok();
	}


	/**
	 * 根据展会id，得到展会下所有案例(只显示已关联的)
	 *  未使用
	 * @param pageIndex
	 * @param pageSize
	 * @param id
	 *            展会id
	 * @return
	 */
	@RequestMapping(value = "queryAllExhibitionApp", method = RequestMethod.GET)
	public ResultPage queryAllExhibitionApp(@RequestParam(defaultValue = "1") Integer pageIndex,
			@RequestParam(defaultValue = "10") Integer pageSize, Integer id) {
		PageInfo info = exhibitionService.findAllExhibitionAppByIdMake(pageIndex, pageSize, id);
		return ResultPage.list(info);
	}

	/**
	 * 根据展会id，得到展会下所有案例(案例都显示，已关联的有标记字段)
	 *  已使用
	 * @param pageIndex
	 * @param pageSize
	 * @param id
	 *            展会id
	 * @return
	 */
	@RequestMapping(value = "queryAllExhibitionAppDetails", method = RequestMethod.GET)
	public   ResultPage  queryAllExhibitionAppDetails(@RequestParam(defaultValue = "1") Integer pageIndex,
			@RequestParam(defaultValue = "10") Integer pageSize, Integer id){
		PageInfo info = exhibitionService.findAllExhibitionAppDetailsByIdMake(pageIndex, pageSize, id);
		return ResultPage.list(info);
	}
	
	/**
	 * 展会添加展会案例
	 * 
	 * @param exhibitionCaseMap
	 *            主要参数，案例id和展会id
	 * @return
	 */
	@RequestMapping(value = "addExhibitionApp", method = RequestMethod.POST)
	public ResultData addExhibitionApp(ExhibitionCaseMap exhibitionCaseMap, HttpServletRequest request) {
		if (exhibitionCaseMap.getCaseId() == null || exhibitionCaseMap.getExhibitionId() == null) {
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		// 由于该表(zl_exhibition_case_map)删除的时候是delete操作，所以不用判断是否是虚拟的删除，但是要判断展会id和案例id是否存在，防止表单重复提交
		exhibitionService.saveExhibitionApp(exhibitionCaseMap, request);
		return ResultData.ok();
	}

	/**
	 * 删除展会案例，根据案例id和展会id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancelExhibitionAppById", method = RequestMethod.POST)
	public ResultData cancelExhibitionAppById(ExhibitionCaseMap exhibitionCaseMap) {
		if (exhibitionCaseMap.getCaseId() == null || exhibitionCaseMap.getExhibitionId() == null) {
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		exhibitionService.removeExhibitionAppByCaseIdAndEid(exhibitionCaseMap);
		return ResultData.ok();
	}

	
	
	
	/**
	 * 得到所有工程师(而且通过展会id和案例id，标识已关联的工程师)，后端查看展会案例关联的工程师,分页获取
	 * @param aid 案例id
	 * @param eid 展会id
	 * @return
	 */
	@RequestMapping(value = "queryAllEngineer", method = RequestMethod.GET)
	public   ResultPage  queryAllEngineer(@RequestParam(defaultValue = "1") Integer pageIndex,
			@RequestParam(defaultValue = "10") Integer pageSize,Integer  aid,Integer  eid){
		PageInfo  info = exhibitionService.findAllEngineer(pageIndex,pageSize,aid,eid);
		return  ResultPage.list(info);
	}
	
	
	/**
	 * 展会案例修改关联工程师
	 * 
	 * @param aid 案例id
	 * @param eid 展会id
	 * @param cid 工程师(知料师)id
	 * @return
	 */
	@RequestMapping(value = "editApplicationToCustomer", method = RequestMethod.POST)
	public ResultData editApplicationToCustomer(Integer  aid,Integer  eid,Integer  cid) {
		// 执行更新操作，如果展会案例已关联工程师，需要把之前的标记为删除，如果之前关联的工程师id和该id一样，也是执行删除
		exhibitionService.alterExhibitionApplicationCase(aid, eid,cid);
		return ResultData.ok();
	}
	
	
	
	/**
	 * 展会案例关联工程师
	 * 未使用
	 * @param exhibitionCase
	 *            主要包含案例id，工程师id
	 * @param exhibitionId
	 *            展会id
	 * @return
	 */
	@RequestMapping(value = "setApplicationToCustomer", method = RequestMethod.POST)
	public ResultData setApplicationToCustomer(ExhibitionCase exhibitionCase, Integer exhibitionId) {
		if (exhibitionCase.getCid() == null || exhibitionCase.getUid() == null) {
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		// 判断该案例是否已属于该展会，属于就执行添加
		exhibitionService.saveExhibitionApplicationCase(exhibitionCase, exhibitionId);
		return ResultData.ok();
	}

	/**
	 * 展会案例取消关联工程师
	 * 未使用
	 * @param exhibitionCase
	 *            主要包含案例id，工程师id
	 * @param exhibitionId
	 *            展会id
	 * @return
	 */
	@RequestMapping(value = "cancelApplicationToCustomer", method = RequestMethod.GET)
	public ResultData cancelApplicationToCustomer(ExhibitionCase exhibitionCase, Integer exhibitionId) {
		if (exhibitionCase.getCid() == null || exhibitionCase.getUid() == null) {
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		// 判断该案例是否已属于该展会，属于在判断展会案例表uid和前端传入的uid是否一样，一样就执行取消操作
		exhibitionService.cancelExhibitionApplicationCase(exhibitionCase, exhibitionId);
		return ResultData.ok();
	}

	

}
