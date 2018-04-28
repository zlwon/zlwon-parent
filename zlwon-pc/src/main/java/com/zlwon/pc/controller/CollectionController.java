package com.zlwon.pc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.collection.PcInsertCollectionDto;
import com.zlwon.dto.pc.collection.QueryMyCollectionPageDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.RedisService;
import com.zlwon.vo.pc.collection.HandleCollectionVo;
import com.zlwon.vo.pc.collection.MyCollectionInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 收藏pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/collection")
public class CollectionController extends BaseController {

	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 处理用户收藏
	 * @param form
	 * @param request
	 * @return 返回收藏id
	 */
	@AuthLogin
	@ApiOperation(value = "处理用户收藏")
    @RequestMapping(value = "/handleCollection", method = RequestMethod.POST)
    public ResultData handleCollection(PcInsertCollectionDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer type = form.getType();  //信息类型，1物性表，2案例，3提问
		Integer iid = form.getIid();  //信息ID
		Integer collectId = form.getCollectId();  //收藏ID
		
		HandleCollectionVo resultVo = new HandleCollectionVo();
		
		//验证收藏ID是否存在
		if(collectId == null){  //新增收藏
			if(type == null || iid == null){
				return ResultData.error(StatusCode.INVALID_PARAM);
			}
			
			//验证该收藏是否存在
			Collection collectInfo = collectionService.findCollectionInfoByUser(type,iid,user.getId());
			if(collectInfo != null){
				return ResultData.error(StatusCode.COLLECTION_IS_EXIST);
			}
			
			Collection temp = new Collection();
			temp.setType(type);
			temp.setUid(user.getId());
			temp.setIid(iid);
			temp.setCreateTime(new Date());
			
			//新增用户收藏
			Collection result = collectionService.insertCollection(temp);
			resultVo.setCollectionId(result.getId()); //收藏ID
			resultVo.setHandleType(1);
			
		}else{  //删除收藏
			int count = collectionService.deleteCollectionById(collectId);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
			resultVo.setCollectionId(null); //收藏ID
			resultVo.setHandleType(2);
		}

		return ResultData.one(resultVo);
	}
	
	/**
	 * 根据信息ID和类型处理用户收藏
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "根据信息ID和类型处理用户收藏")
    @RequestMapping(value = "/handleNoIdCollection", method = RequestMethod.POST)
    public ResultData handleNoIdCollection(PcInsertCollectionDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer type = form.getType();  //信息类型，1物性表，2案例，3提问
		Integer iid = form.getIid();  //信息ID
		
		if(type == null || iid == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		HandleCollectionVo resultVo = new HandleCollectionVo();
		
		//验证该收藏是否存在
		Collection collectInfo = collectionService.findCollectionInfoByUser(type,iid,user.getId());
		if(collectInfo != null){  //删除收藏
			int count = collectionService.deleteCollectionById(collectInfo.getId());
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
			resultVo.setCollectionId(null); //收藏ID
			resultVo.setHandleType(2);
		}else{  //新增收藏
			Collection temp = new Collection();
			temp.setType(type);
			temp.setUid(user.getId());
			temp.setIid(iid);
			temp.setCreateTime(new Date());
			
			//新增用户收藏
			Collection result = collectionService.insertCollection(temp);
			resultVo.setCollectionId(result.getId()); //收藏ID
			resultVo.setHandleType(1);
		}

		return ResultData.one(resultVo);
	}
	
	/**
	 * 根据收藏ID删除用户收藏
	 * @param id
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "根据收藏ID删除用户收藏")
    @RequestMapping(value = "/deleteCollection", method = RequestMethod.GET)
    public ResultData deleteCollection(@RequestParam Integer id,HttpServletRequest request){
        
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer userId = user.getId();  //用户ID
		
		//根据收藏ID查询收藏信息
		Collection myCollect = collectionService.selectCollectionById(id);
		if(myCollect == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//判断是否是当前用户回答
		if(myCollect.getUid() == userId){
			//删除用户收藏
			int count = collectionService.deleteCollectionById(id);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
		}else{
			return ResultData.error(StatusCode.USER_NOT_PERMIT);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * pc端查询我的所有收藏（可指定类型）
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端查询我的所有收藏（可指定类型）")
    @RequestMapping(value = "/queryMyCollectionPage", method = RequestMethod.POST)
    public ResultPage queryMyCollectionPage(QueryMyCollectionPageDto form,HttpServletRequest request){
		
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
		Integer type = form.getType();  //信息类型，1物性表，2案例，3提问

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我的所有收藏
		PageInfo<MyCollectionInfoVo> pageList = collectionService.findMyCollectionPage(form);
		
		return ResultPage.list(pageList);
	}
}
