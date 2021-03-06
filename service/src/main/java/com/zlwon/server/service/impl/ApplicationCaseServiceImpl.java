package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.applicationCase.ApplicationCaseDto;
import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.dto.pc.applicationCase.QueryApplicationCaseListDto;
import com.zlwon.dto.pc.specification.PcSearchSpecCasePageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.CaseEditMapper;
import com.zlwon.rdb.dao.SpecificationParameterMapper;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.CaseEdit;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.SpecificationParameter;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.utils.JsonUtils;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import com.zlwon.vo.applicationCase.ApplicationCaseListVo;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import com.zlwon.vo.applicationCase.ApplicationCaseVo;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;
import com.zlwon.vo.pc.applicationCase.EditApplicationCaseCustomerVo;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseQuestionAndAnswerVo;
import com.zlwon.vo.pc.applicationCase.IndexHotApplicationCaseVo;
import com.zlwon.vo.pc.applicationCase.PcApplicationCaseSimpleVo;
import com.zlwon.vo.pc.applicationCase.QueryApplicationCaseListVo;

/**
 * 应用案例ServiceImpl
 * @author yangy
 *
 */

@Service
public class ApplicationCaseServiceImpl implements ApplicationCaseService {

	@Value("${pc.user.header}")
	private  String  token;
	@Value("${pc.redis.user.token.prefix}")
	private  String  tokenPrefix;
	@Value("${pc.redis.user.token.field}")
	private  String  tokenField;
	@Value("${pc.redis.user.token.make}")
	private  String  tokenMake;
	@Value("${pc.index.hot.applicationCase}")
	private  String  hotApplicationCase;
	//api小程序端请求头保存openid的key
	@Value("${api.user.header}")
	private String openid;
	
	@Autowired
	private ApplicationCaseMapper applicationCaseMapper;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private  RedisService  redisService;
	@Autowired
	private  CaseEditMapper  caseEditMapper;
	@Autowired
	private  SpecificationParameterMapper  specificationParameterMapper;
	
	/**
	 * 根据id查询应用案例
	 * @param id  自增ID
	 * @return
	 */
	@Override
	public ApplicationCase findAppCaseById(Integer id){
		ApplicationCase temp = applicationCaseMapper.findAppCaseById(id);
		return temp;
	}

	/**
	 * 根据标题查询应用案例
	 * @param title  标题
	 * @return
	 */
	@Override
	public ApplicationCase findAppCaseByTitle(String title){
		ApplicationCase temp = applicationCaseMapper.findAppCaseByTitle(title);
		return temp;
	}

	/**
	 * 根据物性表ID查询使用该规格的应用案例
	 * @param specId  物性表ID
	 * @return
	 */
	@Override
	public List<ApplicationCase> findAppCaseBySpecId(Integer specId){
		List<ApplicationCase> list = applicationCaseMapper.findAppCaseBySpecId(specId);
		return list;
	}

	/**
	 * 根据用户ID查询该用户所有的应用案例
	 * @param uid  用户ID
	 * @return
	 */
	@Override
	public List<ApplicationCase> findAppCaseByUid(Integer uid){
		List<ApplicationCase> list = applicationCaseMapper.findAppCaseByUid(uid);
		return list;
	}

	/**
	 * 根据id查询应用案例具体详情
	 * @param id
	 * @return
	 */
	@Override
	public ApplicationCaseDetailVo findAppCaseDetailById(Integer id){
		ApplicationCaseDetailVo temp = applicationCaseMapper.findAppCaseDetailById(id);
		return temp;
	}



	/**
	 * 得到所有案例，包含案例id，案例名称，物性规格，应用行业，应用市场，生产商，基材,案例名称模糊查询
	 * @param key  关键字，案例名称模糊查询 
	 * @param resultPage  包含页码和页个数
	 */
	@Override
	public PageInfo<ApplicationCaseListVo> findAllApplicationCase(Integer  pageIndex,Integer  pageSize,String  key) {
		PageHelper.startPage(pageIndex, pageSize);
		List<ApplicationCaseListVo> list = applicationCaseMapper.selectAllApplicationCaseDetails(key);
		PageInfo<ApplicationCaseListVo>  info = new  PageInfo<>(list);
		return info;
	}

	/**
	 * 根据案例id，保存修改后的案例信息
	 */
	@Override
	public long alterApplicateCaseById(ApplicationCaseDto applicationCaseDto) {
		ApplicationCase app = applicationCaseMapper.findAppCaseById(applicationCaseDto.getId());
		if(app == null || app.getDel() != 1)
			throw new  CommonException(StatusCode.DATA_NOT_EXIST);

		//判断标题是否重复
		List<ApplicationCase> list = applicationCaseMapper.selectApplicationCaseByTitleMake(applicationCaseDto.getTitle());
		if(list!=null &&  list.size()>0){
			if(list.size()==1){
				//判断id是否一样
				if(applicationCaseDto.getId()!=list.get(0).getId().intValue()){
					throw new  CommonException(StatusCode.DATA_IS_EXIST);
				}
			}else {
				throw new  CommonException(StatusCode.DATA_IS_EXIST);
			}
		}
		
		
		//根据应用产品名称和终端客户名称得到案例物性参数表的id
		if(StringUtils.isNotBlank(applicationCaseDto.getTerminal())){
			//根据类型和名称，得到参数信息，如果没有这条信息，就执行添加操作
			SpecificationParameter  terminalParam = specificationParameterMapper.selectByTypeAndName(8,applicationCaseDto.getTerminal());
			if(terminalParam == null){
				terminalParam = new  SpecificationParameter();
				terminalParam.setClassType(8);//设置终端客户类型
				terminalParam.setName(applicationCaseDto.getTerminal());
				//执行添加操作
				specificationParameterMapper.insert(terminalParam);
			}
			//把id赋值到dto中
			applicationCaseDto.setTerminalId(terminalParam.getId());
		}
		
		//根据应用产品名称和终端客户名称得到案例物性参数表的id
		if(StringUtils.isNotBlank(applicationCaseDto.getAppProduct())){
			//根据类型和名称，得到参数信息，如果没有这条信息，就执行添加操作
			SpecificationParameter  appProductParam = specificationParameterMapper.selectByTypeAndName(9,applicationCaseDto.getAppProduct());
			if(appProductParam == null){
				appProductParam = new  SpecificationParameter();
				appProductParam.setClassType(9);//设置应用产品类型
				appProductParam.setName(applicationCaseDto.getAppProduct());
				//执行添加操作
				specificationParameterMapper.insert(appProductParam);
			}
			//把id赋值到dto中
			applicationCaseDto.setAppProductId(appProductParam.getId());
		}
		
		
		
		ApplicationCase applicationCase = new  ApplicationCase();
		try {
			BeanUtils.copyProperties(applicationCase, applicationCaseDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		//执行更新操作
		return   applicationCaseMapper.updateByPrimaryKeySelective(applicationCase);
	}

	/**
	 * 根据案例id，删除案例
	 */
	@Override
	public long removeApplicateCaseById(Integer id) {
		ApplicationCase app = applicationCaseMapper.findAppCaseById(id);
		if(app==null || -1 == app.getDel())
			throw new  CommonException(StatusCode.DATA_NOT_EXIST);

		//执行更新操作
		ApplicationCase applicationCase = new ApplicationCase();
		applicationCase.setId(id);
		applicationCase.setDel(-1);
		return   applicationCaseMapper.updateByPrimaryKeySelective(applicationCase);
	}

	/**
	 * 添加案例
	 */
	@Transactional
	@Override
	public long saveApplicateCase(HttpServletRequest  request,ApplicationCaseDto applicationCaseDto,Integer  type) {
		//根据要添加的案例标题，得到案例
		List<ApplicationCase> list = applicationCaseMapper.selectApplicationCaseByTitleMake(applicationCaseDto.getTitle());
		if(list != null  && list.size() > 0){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		
		//根据应用产品名称和终端客户名称得到案例物性参数表的id
		if(StringUtils.isNotBlank(applicationCaseDto.getTerminal())){
			//根据类型和名称，得到参数信息，如果没有这条信息，就执行添加操作
			SpecificationParameter  terminalParam = specificationParameterMapper.selectByTypeAndName(8,applicationCaseDto.getTerminal());
			if(terminalParam == null){
				terminalParam = new  SpecificationParameter();
				terminalParam.setClassType(8);//设置终端客户类型
				terminalParam.setName(applicationCaseDto.getTerminal());
				//执行添加操作
				specificationParameterMapper.insert(terminalParam);
			}
			//把id赋值到dto中
			applicationCaseDto.setTerminalId(terminalParam.getId());
		}
		
		//根据应用产品名称和终端客户名称得到案例物性参数表的id
		if(StringUtils.isNotBlank(applicationCaseDto.getAppProduct())){
			//根据类型和名称，得到参数信息，如果没有这条信息，就执行添加操作
			SpecificationParameter  appProductParam = specificationParameterMapper.selectByTypeAndName(9,applicationCaseDto.getAppProduct());
			if(appProductParam == null){
				appProductParam = new  SpecificationParameter();
				appProductParam.setClassType(9);//设置应用产品类型
				appProductParam.setName(applicationCaseDto.getAppProduct());
				//执行添加操作
				specificationParameterMapper.insert(appProductParam);
			}
			//把id赋值到dto中
			applicationCaseDto.setAppProductId(appProductParam.getId());
		}
		
		
		
		ApplicationCase applicationCase = new  ApplicationCase();
		try {
			BeanUtils.copyProperties(applicationCase, applicationCaseDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果是用户添加案例，设置用户id
		if(0 == type){
			//得到当前用户信息
			Customer record = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
			applicationCase.setUid(record.getId());
		}
		applicationCase.setCreateTime(new  Date());
		applicationCase.setExamine(type);//1管理员，审核状态默认为通过0用户，审核状态为审核中
		long id = applicationCaseMapper.insertSelective(applicationCase);
		ApplicationCase case1 = applicationCaseMapper.findAppCaseById(applicationCase.getId());
		System.out.println("-"+case1);
		return  applicationCase.getId();
	}

	/**
	 * 分页查询特定展会的案例简单详情
	 * @param info
	 * @return
	 */
	@Override
	public PageInfo<ApplicationCaseSimpleVo> selectSpecifyExhibitionCase(SearchSpecifyExhibitionDto info){
		PageHelper.startPage(info.getCurrentPage(), info.getPageSize());
		List<ApplicationCaseSimpleVo> list = applicationCaseMapper.selectSpecifyExhibitionCase(info);
		PageInfo<ApplicationCaseSimpleVo> result = new PageInfo<ApplicationCaseSimpleVo>(list);
		return result;
	}

	
	/**
	 * 根据案例id，得到案例详情
	 * @param id 案例id
	 * @param type 1pc,2api
	 * @return
	 */
	public ApplicationCaseDetailsVo findApplicationCaseDetailsMake(Integer id,HttpServletRequest  request,Integer type) {
		//得到当前用户信息
		Customer re = null;
		if(type.equals(1)){
			//1pc，去redis中查找pc用户信息
			re = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		}else if (type.equals(2)) {
			//2api，去redis中查找api用户信息，openid是key
			re = CustomerUtil.getCustomer2Redis(request, openid, redisService);
		}
		ApplicationCaseDetailsVo  record = applicationCaseMapper.selectApplicationCaseDetailsMake(id,re == null?null:re.getId());
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//得到最近编辑案例的用户信息(审核通过的)
		Customer  customer = caseEditMapper.selectOneEditCaseCustomer(record.getId());
		if(customer != null){
			record.setUid(customer.getId());//设置编辑案例的用户id
			record.setNickname(customer.getNickname());
			record.setHeaderimg(customer.getHeaderimg());
			record.setMobile(customer.getMobile());
			record.setCreateTime(customer.getCreateTime());//设置最新编辑时间
		}else {
			record.setUid(null);//设置编辑案例用户id为null
		}
		return record;
	}

	
	/**
	 * 根据物性ID分页查询关联应用案例
	 * @param info
	 * @return
	 */
	@Override
	public PageInfo<PcApplicationCaseSimpleVo> findSpecCaseBySpecIdPage(PcSearchSpecCasePageDto info){
		PageHelper.startPage(info.getCurrentPage(), info.getPageSize());
		List<PcApplicationCaseSimpleVo> list = applicationCaseMapper.selectSpecCaseBySpecIdPage(info.getSpecId());
		PageInfo<PcApplicationCaseSimpleVo> result = new PageInfo<PcApplicationCaseSimpleVo>(list);
		return result;
	}
	
	/**
	 * 根据物性ID查询关联应用案例
	 * @param specId
	 * @return
	 */
	@Override
	public List<PcApplicationCaseSimpleVo> findSpecCaseBySpecIdList(Integer specId){
		List<PcApplicationCaseSimpleVo> list = applicationCaseMapper.selectSpecCaseBySpecIdPage(specId);
		return list;
	}
	
	/**
	 * 根据物性ID统计物性表关联案例数量
	 * @param specId
	 * @return
	 */
	@Override
	public int countSpecCaseBySpecId(Integer specId){
		int count = applicationCaseMapper.countSpecCaseBySpecId(specId);
		return count;
	}

	
	/**
	 * 用户编辑案例信息
	 * 只有认证用户才可以编辑
	 */
	public int alterApplicationCaseByUser(HttpServletRequest request, ApplicationCase applicationCase) {
		//查看案例是否存在
		ApplicationCase app = applicationCaseMapper.findAppCaseById(applicationCase.getId());
		if(app == null || -1 == app.getDel()){
			throw new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//得到当前用户信息
		Customer record = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		//只有认证用户才可以编辑
		if(record.getRole() != 1 && record.getRole() != 6){
			throw  new  CommonException(StatusCode.PERMIT_USER_AUTHENTIC_LIMIT);
		}
		
		//查看用户对该案例有没有未审核的案例，如果有，不让添加，抛出异常，否则添加
		CaseEdit  caseEdit = caseEditMapper.selectByUidAndAidExamine(record.getId(),app.getId());
		//判断审核状态,如果有审核中，不让添加，抛出异常
		if(caseEdit != null){
			/*caseEdit.setCreateTime(new  Date());
			caseEdit.setExamine(0);
			caseEdit.setSelectRequirements(applicationCase.getSelectRequirements() == null || applicationCase.getSelectRequirements().trim().equals("")? app.getSelectRequirements():applicationCase.getSelectRequirements());
			caseEdit.setSelectCause(applicationCase.getSelectCause() == null || applicationCase.getSelectCause().trim().equals("")? app.getSelectCause():applicationCase.getSelectCause());
			caseEdit.setSetting(applicationCase.getSetting() == null || applicationCase.getSetting().trim().equals("") ? app.getSetting():applicationCase.getSetting());
			//执行更新操作
			return caseEditMapper.updateByPrimaryKeySelective(caseEdit);*/
			throw  new  CommonException(StatusCode.DATA_NOT_EXAMINE);
		}else {
			//校验选材要求每行不超过40字
			checkoutCaseEditNumber(applicationCase, 1);
			//校验选材原因每行不超过40字
			checkoutCaseEditNumber(applicationCase, 2);
			
			caseEdit = new CaseEdit();
			caseEdit.setAid(app.getId());
			caseEdit.setUid(record.getId());
			caseEdit.setExamine(0);
			caseEdit.setCreateTime(new  Date());
//			caseEdit.setSelectRequirements(applicationCase.getSelectRequirements() == null || applicationCase.getSelectRequirements().trim().equals("")? app.getSelectRequirements():applicationCase.getSelectRequirements());
			caseEdit.setSelectRequirements(StringUtils.isBlank(applicationCase.getSelectRequirements())?null:applicationCase.getSelectRequirements());
//			caseEdit.setSelectCause(applicationCase.getSelectCause() == null || applicationCase.getSelectCause().trim().equals("")? app.getSelectCause():applicationCase.getSelectCause());
			caseEdit.setSelectCause(StringUtils.isBlank(applicationCase.getSelectCause())?null:applicationCase.getSelectCause());
//			caseEdit.setSetting(applicationCase.getSetting() == null|| applicationCase.getSetting().trim().equals("") ? app.getSetting():applicationCase.getSetting());
			caseEdit.setSetting(StringUtils.isBlank(applicationCase.getSetting())?null:applicationCase.getSetting());
			//执行添加操作
			return  caseEditMapper.insertSelective(caseEdit);
		}
		
		
	}

	/**
	 * 根据案例id，包含生产商id，把生产商id映射到ApplicationCase的用户id上
	 */
	@Override
	public ApplicationCaseVo findAppCaseDetailsById(Integer id) {
		ApplicationCaseVo vo = applicationCaseMapper.selectAppCaseDetailsById(id);
		return   vo;
	}

	
	/**
	 * 得到所有案例，条件查询，分页
	 * @param pageIndex
	 * @param pageSize
	 * @param listDto 条件查询
	 * @param type 1pc,2api
	 * @return
	 */
	public PageInfo findAllApplicationCaseSelective(HttpServletRequest  request,Integer pageIndex, Integer pageSize,
			QueryApplicationCaseListDto listDto,Integer  type) {
		//得到当前用户信息
		Customer record = null;
		if(type.equals(1)){
			//1pc，去redis中查找pc用户信息
			record = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		}else if (type.equals(2)) {
			//2api，去redis中查找api用户信息，openid是key
			record = CustomerUtil.getCustomer2Redis(request, openid, redisService);
		}
		if(record == null){
			listDto.setUid(null);
		}else {
			listDto.setUid(record.getId());
		}
		
		PageHelper.startPage(pageIndex, pageSize);
		List<QueryApplicationCaseListVo>  list = applicationCaseMapper.selectAllApplicationCaseSelective(listDto);
		return new  PageInfo(list);
	}

	
	/**
	 * 首页热门案例查询
	 */
	public List<IndexHotApplicationCaseVo> findHotApplicationCase() {
		String jsonValue = redisService.get(hotApplicationCase);
		if(jsonValue != null){
			return   JsonUtils.jsonToList(jsonValue, IndexHotApplicationCaseVo.class);
		}
		
		
		List<IndexHotApplicationCaseVo> applicationCaseVoList = applicationCaseMapper.selectHotApplicationCase();
		//由于案例和问答没有关系，所以就不需要了
		/*if(applicationCaseVoList != null  && applicationCaseVoList.size() > 0 ){
			for (IndexHotApplicationCaseVo indexHotApplicationCaseVo : applicationCaseVoList) {
				//根据案例id，得到提问信息
				List<IndexHotApplicationCaseQuestionAndAnswerVo> questionAndAnswerVoList = applicationCaseMapper.selectHotApplicationCaseQuestionByAid(indexHotApplicationCaseVo.getId());
				
				if(questionAndAnswerVoList != null  &&  questionAndAnswerVoList.size() > 0){
					for (IndexHotApplicationCaseQuestionAndAnswerVo indexHotApplicationCaseQuestionAndAnswerVo : questionAndAnswerVoList) {
						//通过提问id，得到回答信息
						String  content = applicationCaseMapper.selectselectHotApplicationCaseAnswerByQid(indexHotApplicationCaseQuestionAndAnswerVo.getId());
						indexHotApplicationCaseQuestionAndAnswerVo.setContent(content);
					}
				}
				indexHotApplicationCaseVo.setQuestionAndAnswerVo(questionAndAnswerVoList);
			}
		}*/
		redisService.set(hotApplicationCase, JsonUtils.objectToJson(applicationCaseVoList), 30,TimeUnit.DAYS);
		return applicationCaseVoList;
	}

	
	/**
	 * 设置案例为热门，需要判断热门个数是否少于5个，并且当前案例不是热门
	 * 注意：要清除缓存
	 * @param id
	 * @return
	 */
	public int alterApplicationCaseHot(Integer id) {
		//查看案例是否存在,并且是否已是热门案例
		ApplicationCase app = applicationCaseMapper.findAppCaseById(id);
		if(app == null || -1 == app.getDel() || 1 == app.getHot()){
			throw new  CommonException((app == null || -1 == app.getDel()) ? StatusCode.DATA_NOT_EXIST : StatusCode.APP_IS_HOT);
		}
		//查看已是热门的案例是否小于5个，热门案例不能超过5个
		int   count = applicationCaseMapper.selectHotAppCount();
		if(count >= 5){
			throw  new  CommonException(StatusCode.APP_IS_MAX);
		}
		//清空redis缓存
		redisService.delete(hotApplicationCase);
		//设置当前案例为热门案例
		app.setHot(1);
		return  (int) applicationCaseMapper.updateByPrimaryKeySelective(app);
	}

	/**
	 * 取消热门案例
	 * 注意：要清除缓存
	 */
	public int removeHotApplicationCase(Integer id) {
		//查看案例是否存在,并且是否是非热门案例
		ApplicationCase app = applicationCaseMapper.findAppCaseById(id);
		if(app == null || -1 == app.getDel() || 0 == app.getHot()){
			throw new  CommonException((app == null || -1 == app.getDel()) ? StatusCode.DATA_NOT_EXIST : StatusCode.APP_IS_NOT_HOT);
		}
		//清空redis缓存
		redisService.delete(hotApplicationCase);
		//设置案例为非热门
		app.setHot(0);
		return  (int) applicationCaseMapper.updateByPrimaryKeySelective(app);
	}

	/**
	 * 根据案例id，得到编辑过案例的用户信息，分类型获取
	 * @param id 案例id
	 * @param type 类型0：所有1：案例背景2：选材原因3：选材要求
	 * @return
	 */
	@Override
	public List<EditApplicationCaseCustomerVo> findEditApplicationCaseCustomerById(Integer id,int  type) {
		List<EditApplicationCaseCustomerVo>  list = caseEditMapper.selectEditApplicationCaseCustomerById(id,type);
		return list;
	}

	//校验选材要求(选材原因)每行不超过40字,applicationCase肯定不为null，
	//type 1:选材要求2：选材原因
	//但是要判断选材要求(选材原因)字段是否为null
	private   void  checkoutCaseEditNumber(ApplicationCase applicationCase,Integer   type){
		String key = type == 1?applicationCase.getSelectRequirements():applicationCase.getSelectCause();
		if(StringUtils.isNotBlank(key)){
			String[] split = key.split("\n");
			for (int i = 0; i < split.length; i++) {
				if(split[i].length() > 40){
					throw  new  CommonException(StatusCode.EDIT_CASE_LENGTH_LONG);
				}
			}
		}
	}
}
