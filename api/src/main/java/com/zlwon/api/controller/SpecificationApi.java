package com.zlwon.api.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.api.specification.ChangeCharacterRecordApiDto;
import com.zlwon.dto.api.specification.QueryWCSpecByPageDto;
import com.zlwon.dto.api.specification.QueryWCSpecDealerdDto;
import com.zlwon.dto.api.specification.QueryWCSpecRelatedCaseDto;
import com.zlwon.rdb.entity.CharacteristicRecord;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CharacteristicRecordService;
import com.zlwon.server.service.CharacteristicService;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.DealerdQuotationService;
import com.zlwon.server.service.SpecificationParameterService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.pc.applicationCase.PcApplicationCaseSimpleVo;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;
import com.zlwon.vo.specification.SpecificationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 小程序端物性接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/specification")
public class SpecificationApi extends BaseApi {

	@Autowired
	private SpecificationService specificationService;
	
	@Autowired
	private CharacteristicService characteristicService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private SpecificationParameterService specificationParameterService;
	
	@Autowired
	private CharacteristicRecordService characteristicRecordService;
	
	@Autowired
	private ApplicationCaseService applicationCaseService;
	
	@Autowired
	private DealerdQuotationService dealerdQuotationService;
	
	/**
	 * 分页查询物性表信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分页查询物性表信息")
    @RequestMapping(value = "/queryWCSpecByPage", method = RequestMethod.POST)
	public ResultPage queryWCSpecByPage(QueryWCSpecByPageDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer manufacturerId = form.getManufacturerId();  //生产商ID
		Integer baseMaterialId = form.getBaseMaterialId();  //基材ID
		Integer fillerId = form.getFillerId();  //填充物ID
		Integer safeCertifyId = form.getSafeCertifyId();  //安规认证ID
		String searchText = form.getSearchText();  //搜索关键词

		if(currentPage == null || pageSize == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
		if(user == null){  //未登录
			form.setUserId(null);
		}else{  //已登录
			form.setUserId(user.getId());
		}
		
		PageInfo<SpecificationDetailVo> pageList = specificationService.findWCSpecByPage(form);
		
		return ResultPage.list(pageList);
	}
	
	
	/**
	 * 根据物性表ID查询物性表详情
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据物性表ID查询物性表详情")
    @RequestMapping(value = "/querySpecInfoById", method = RequestMethod.GET)
	public ResultData querySpecificationById(@RequestParam Integer id,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = getRedisLoginCustomer(token);
		
		//根据物性Id查询物性规格信息
		SpecificationDetailVo temp = specificationService.findSpecDetailById(id);
		
		if(temp != null){
			Integer userId = null; //用户Id
			
			//查询收藏信息
			if(user == null){
				temp.setIsCollect(0);
			}else{
				userId = user.getId();
				Collection collectInfo = collectionService.findCollectionInfoByUser(1,id,user.getId());
				if(collectInfo != null){
					temp.setCollectId(collectInfo.getId());
					temp.setIsCollect(1);
				}else{
					temp.setIsCollect(0);
				}
			}
			
			//根据物性规格ID和当前用户ID查询标签详情
			List<CharacteristicDetailVo> characterList = characteristicService.findCharacteristicGroupByUserSepcId(id,userId);
			temp.setCharacterTap(characterList);
			
			//根据填充材质字符串查询填充材质
			if(StringUtils.isNotBlank(temp.getFiller())){
				List<SpecificationParameter> fillerList = specificationParameterService.findSpecificationParameterByIdStr(temp.getFiller());
				//处理拼接
				if(fillerList != null && fillerList.size() > 0){
					temp.setFillerList(fillerList);
					String fillerStr = "";
					for(SpecificationParameter fillTemp : fillerList){
						fillerStr = fillerStr + fillTemp.getName() + ",";
					}
					fillerStr = fillerStr.substring(0, fillerStr.length()-1);
					temp.setFiller(fillerStr);
				}
			}
			
			//根据安规认证字符串查询安规认证
			if(StringUtils.isNotBlank(temp.getSafetyCert())){
				List<SpecificationParameter> safetyCertificyList = specificationParameterService.findSpecificationParameterByIdStr(temp.getSafetyCert());
				//处理拼接
				if(safetyCertificyList != null && safetyCertificyList.size() > 0){
					temp.setSafetyCertificyList(safetyCertificyList);
					String safetyCertificyStr = "";
					for(SpecificationParameter safetyTemp : safetyCertificyList){
						safetyCertificyStr = safetyCertificyStr + safetyTemp.getName() + ",";
					}
					safetyCertificyStr = safetyCertificyStr.substring(0, safetyCertificyStr.length()-1);
					temp.setSafetyCert(safetyCertificyStr);
				}
			}
		}
		
		return ResultData.one(temp);
	}
	
	/**
	 * 小程序端新增/删除物性标签点赞记录
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "小程序端新增/删除物性标签点赞记录")
    @RequestMapping(value = "/changeCharacterRecord", method = RequestMethod.POST)
	public ResultData changeCharacterRecord(ChangeCharacterRecordApiDto form,HttpServletRequest request){
		
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
		
		Integer characteristicId = form.getCharacteristicId();  //物性标签ID
		
		if(characteristicId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer userId = user.getId();  //用户ID
		Integer status = 0;  //0：取消点赞成功，1：点赞成功
		
		//根据用户ID和回答ID查询点赞记录
		CharacteristicRecord valid = characteristicRecordService.findCharacteristicRecordByUserCharacterId(characteristicId, userId);
		if(valid == null){  //用户未点赞，执行点赞
			CharacteristicRecord newRecord = new CharacteristicRecord();
			newRecord.setUid(userId);
			newRecord.setCharacterId(characteristicId);
			newRecord.setCreateTime(new Date());
			
			int count = characteristicRecordService.insertCharacteristicRecord(newRecord);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
			status = 1;
		}else{
			int count = characteristicRecordService.deleteCharacteristicRecordById(valid.getId());
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
			status = 0;
		}
		
		return ResultData.one(status);
	}
	
	/**
	 * 根据物性表ID查询行情与渠道（经销商）
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据物性表ID查询行情与渠道（经销商）")
    @RequestMapping(value = "/queryWCSpecDealerd", method = RequestMethod.POST)
	public ResultData queryWCSpecDealerd(QueryWCSpecDealerdDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer specId = form.getSpecId();  //物性表ID
		
		if(specId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		List<DealerdQuotationDetailVo> list = dealerdQuotationService.findDealerdQuotationBySpecId(specId);
		
		return ResultData.one(list);
	}
	
	/**
	 * 根据物性表ID查询关联案例
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据物性表ID查询关联案例")
    @RequestMapping(value = "/queryWCSpecRelatedCase", method = RequestMethod.POST)
	public ResultData queryWCSpecRelatedCase(QueryWCSpecRelatedCaseDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer specId = form.getSpecId();  //物性表ID
		
		if(specId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		List<PcApplicationCaseSimpleVo> list = applicationCaseService.findSpecCaseBySpecIdList(specId);
		
		return ResultData.one(list);
	}
	
	/**
	 * 根据类型和父ID查询物性参数
	 * @param type
	 * @param parentId
	 * @return
	 */
	@ApiOperation(value = "根据类型和父ID查询物性参数")
    @RequestMapping(value = "/querySpecParamByTypeParent", method = RequestMethod.GET)
	public ResultData querySpecParamByTypeParent(@RequestParam Integer type,@RequestParam Integer parentId,HttpServletRequest request){
		
		if(type == null || parentId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据类型和父ID查询物性参数
		List<SpecificationParameter> specParamList = specificationParameterService.findSpecificationParameterByClasstypeParent(type,parentId);
		
		return ResultData.one(specParamList);
	}
}
