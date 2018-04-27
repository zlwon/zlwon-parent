package com.zlwon.pc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.collection.JudgeCollectionDto;
import com.zlwon.dto.pc.specification.PcSearchAttributeDataPageDto;
import com.zlwon.dto.pc.specification.PcSearchProcessAdvicePageDto;
import com.zlwon.dto.pc.specification.PcSearchSpecCasePageDto;
import com.zlwon.dto.pc.specification.PcSearchSpecDealerPageDto;
import com.zlwon.dto.pc.specification.PcSearchSpecPageDto;
import com.zlwon.nosql.entity.SpecAttributeData;
import com.zlwon.nosql.entity.SpecProcessAdvice;
import com.zlwon.nosql.entity.SpecificationData;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Attribute;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.AttributeService;
import com.zlwon.server.service.CharacteristicSpecMapService;
import com.zlwon.server.service.CollectionService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.DealerdQuotationService;
import com.zlwon.server.service.ProcessingAdviceService;
import com.zlwon.server.service.RedisService;
import com.zlwon.server.service.SpecificationParameterService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.pc.applicationCase.PcApplicationCaseSimpleVo;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;
import com.zlwon.vo.pc.processAdvice.ProcessingAdviceDetailVo;
import com.zlwon.vo.pc.specification.SpecSearchHeaderVo;
import com.zlwon.vo.specification.SpecificationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 物性表pc端接口
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/specification")
public class SpecificationController extends BaseController  {

	@Autowired
	private SpecificationService specificationService;
	
	@Autowired
	private SpecificationParameterService specificationParameterService;
	
	@Autowired
	private CharacteristicSpecMapService characteristicSpecMapService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private DealerdQuotationService dealerdQuotationService;
	
	@Autowired
	private ApplicationCaseService applicationCaseService;
	
	@Autowired
	private AttributeService attributeService;
	
	@Autowired
	private ProcessingAdviceService processingAdviceService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据物性ID查询物性表详情
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据物性表ID查询物性表详情")
    @RequestMapping(value = "/querySpecInfoById", method = RequestMethod.GET)
	public ResultData querySpecInfoById(@RequestParam Integer id,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}

		//根据物性表ID查询物性表详情
		SpecificationDetailVo temp = specificationService.findSpecDetailById(id);
		if(temp == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//根据物性规格ID查询标签详情
		List<CharacteristicDetailVo> characterList = characteristicSpecMapService.selectCharacteristicSpecMapBySepcId(id);
		temp.setCharacterTap(characterList);
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){  //未登录
			temp.setIsCollect(0);
		}else{  //已登录
			Collection collectInfo = collectionService.findCollectionInfoByUser(1,id,user.getId());
			if(collectInfo != null){
				temp.setCollectId(collectInfo.getId());
				temp.setIsCollect(1);
			}else{
				temp.setIsCollect(0);
			}
		}
		
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
		
		return ResultData.one(temp);
	}
	
	/**
	 * pc端分页查询物性表信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端分页查询物性表信息")
    @RequestMapping(value = "/querySpecifyByPcPage", method = RequestMethod.POST)
    public ResultPage querySpecifyByPcPage(PcSearchSpecPageDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		String manufacturerStr = form.getManufacturerStr();  //生产商字符串
		String brandNameStr = form.getBrandNameStr();  //商标字符串
		String baseMaterialStr = form.getBaseMaterialStr();  //基材字符串
		String fillerStr = form.getFillerStr();  //填充物字符串
		String safeCertifyStr = form.getSafeCertifyStr();  //安规认证字符串
		String searchText = form.getSearchText();  //填写搜索栏字符串
		
		if(currentPage == null || pageSize == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){  //未登录
			form.setUserId(null);
		}else{  //已登录
			form.setUserId(user.getId());
		}
		
		//分页查询物性表信息
		PageInfo<SpecificationDetailVo> pageList = specificationService.findSpecifyByPcPage(form);

		return ResultPage.list(pageList);
	}
	
	/**
	 * 查询物性表搜索筛选信息
	 * @return
	 */
	@ApiOperation(value = "查询物性表搜索筛选信息")
    @RequestMapping(value = "/querySpecSearchHeader", method = RequestMethod.GET)
	public ResultData querySpecSearchHeader(){
		
		//获取全部商标
		//List<SpecificationParameter> brandNameList = specificationParameterService.findSpecificationParameterByClasstype(1);

		//获取全部基材
		List<SpecificationParameter> baseMaterialList = specificationParameterService.findSpecificationParameterByClasstype(2);
		
		//获取全部填充物
		List<SpecificationParameter> fillerList = specificationParameterService.findSpecificationParameterByClasstype(3);

		//获取父ID为0的安规认证
		List<SpecificationParameter> safeCertifyList = specificationParameterService.findSpecificationParameterByClasstypeParent(5,0);
		
		//获取全部生产商
		List<Customer> manufacturerList = customerService.findCustomerByRole(2);
		
		SpecSearchHeaderVo result = new SpecSearchHeaderVo();
		//result.setBrandNameList(brandNameList);
		result.setBaseMaterialList(baseMaterialList);
		result.setManufacturerList(manufacturerList);
		result.setFillerList(fillerList);
		result.setSafeCertifyList(safeCertifyList);
		
		return ResultData.one(result);
	}
	
	/**
	 * 根据类型和父ID查询物性参数
	 * @param type
	 * @param parentId
	 * @return
	 */
	@ApiOperation(value = "根据类型和父ID查询物性参数")
    @RequestMapping(value = "/querySpecParamByTypeParent", method = RequestMethod.GET)
	public ResultData querySpecParamByTypeParent(@RequestParam Integer type,@RequestParam Integer parentId){
		
		//根据类型和父ID查询物性参数
		List<SpecificationParameter> specParamList = specificationParameterService.findSpecificationParameterByClasstypeParent(type,parentId);
		
		return ResultData.one(specParamList);
	}
	
	/**
	 * 发送对应物性表pdf到用户邮箱
	 * @param id
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "发送对应物性表pdf到用户邮箱")
    @RequestMapping(value = "/sendSpecAttachToMail", method = RequestMethod.GET)
	public ResultData sendSpecAttachToMail(@RequestParam Integer id,HttpServletRequest request){
		
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

		//根据物性表ID查询物性表详情
		SpecificationDetailVo specInfo = specificationService.findSpecDetailById(id);
		
		String userMail = user.getEmail();  //获取用户邮箱地址
        String fileUrl = specInfo.getPdf();  //获取文件地址
        String specName = specInfo.getName();  //规格名称
		
		return ResultData.ok();
	}
	
	/**
	 * pc端分页查询物性表经销商报价信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端分页查询物性表经销商报价信息")
    @RequestMapping(value = "/querySpecDealerByPcPage", method = RequestMethod.POST)
    public ResultPage querySpecDealerByPcPage(PcSearchSpecDealerPageDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer specId = form.getSpecId();  //物性表ID
		
		if(currentPage == null || pageSize == null || specId == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}

		//分页查询物性表经销商报价信息
		PageInfo<DealerdQuotationDetailVo> pageList = dealerdQuotationService.findDealerdQuotationDetail(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端分页查询物性表关联案例信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端分页查询物性表关联案例信息")
    @RequestMapping(value = "/querySpecCaseByPcPage", method = RequestMethod.POST)
    public ResultPage querySpecCaseByPcPage(PcSearchSpecCasePageDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer specId = form.getSpecId();  //物性表ID
		
		if(currentPage == null || pageSize == null || specId == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}

		//分页查询物性表关联案例信息
		PageInfo<PcApplicationCaseSimpleVo> result = applicationCaseService.findSpecCaseBySpecIdPage(form);
		
		return ResultPage.list(result);
	}
	
	/**
	 * pc端分页查询物性表加工建议信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端分页查询物性表加工建议信息")
    @RequestMapping(value = "/queryProcessAdviceByPcPage", method = RequestMethod.POST)
    public ResultPage queryProcessAdviceByPcPage(PcSearchProcessAdvicePageDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer specId = form.getSpecId();  //物性表ID
		
		if(currentPage == null || pageSize == null || specId == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//分页查询物性表加工建议信息
		PageInfo<ProcessingAdviceDetailVo> pageList = processingAdviceService.findProcessAdviceBySpecIdPage(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * pc端分页查询物性表属性数据信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端分页查询物性表属性数据信息")
    @RequestMapping(value = "/queryAttributeDataByPcPage", method = RequestMethod.POST)
    public ResultPage queryAttributeDataByPcPage(PcSearchAttributeDataPageDto form,HttpServletRequest request){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer specId = form.getSpecId();  //物性表ID
		String className = form.getClassName();  //分类
		
		if(currentPage == null || pageSize == null || specId == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//分页查询物性表属性数据信息
		PageInfo<Attribute> pageList = attributeService.findAttributeBySpecIdPage(form);
		
		return ResultPage.list(pageList);
	}
}
