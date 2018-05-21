package com.zlwon.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CharacteristicService;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.SpecificationParameterService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
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
	
	
}
