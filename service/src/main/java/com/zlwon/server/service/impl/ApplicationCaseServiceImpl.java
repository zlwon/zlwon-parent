package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.dto.pc.applicationCase.QueryApplicationCaseListDto;
import com.zlwon.dto.pc.specification.PcSearchSpecCasePageDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.CaseEditMapper;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.CaseEdit;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.utils.JsonUtils;
import com.zlwon.vo.applicationCase.ApplicationCaseDetailVo;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import com.zlwon.vo.pc.applicationCase.ApplicationCaseDetailsVo;
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
	
	@Autowired
	private ApplicationCaseMapper applicationCaseMapper;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private  RedisService  redisService;
	@Autowired
	private  CaseEditMapper  caseEditMapper;
	
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
	 * 得到所有案例，包含案例id，案例名称，物性规格，应用行业，应用市场，生产商，基材
	 * @param resultPage  包含页码和页个数
	 */
	@Override
	public PageInfo<ApplicationCase> findAllApplicationCase(Integer  pageIndex,Integer  pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<ApplicationCase> list = applicationCaseMapper.selectAllApplicationCaseDetails();
		PageInfo<ApplicationCase>  info = new  PageInfo<>(list);
		return info;
	}

	/**
	 * 根据案例id，保存修改后的案例信息
	 */
	@Override
	public long alterApplicateCaseById(ApplicationCase applicationCase) {
		ApplicationCase app = applicationCaseMapper.findAppCaseById(applicationCase.getId());
		if(app == null || app.getDel() != 1)
			throw new  CommonException(StatusCode.DATA_NOT_EXIST);

		//判断标题是否重复
		List<ApplicationCase> list = applicationCaseMapper.selectApplicationCaseByTitleMake(applicationCase.getTitle());
		if(list!=null &&  list.size()>0){
			if(list.size()==1){
				//判断id是否一样
				if(applicationCase.getId()!=list.get(0).getId()){
					throw new  CommonException(StatusCode.DATA_IS_EXIST);
				}
			}else {
				throw new  CommonException(StatusCode.DATA_IS_EXIST);
			}
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
	@Override
	public long saveApplicateCase(HttpServletRequest  request,ApplicationCase applicationCase,Integer  type) {
		//根据要添加的案例标题，得到案例
		List<ApplicationCase> list = applicationCaseMapper.selectApplicationCaseByTitleMake(applicationCase.getTitle());
		if(list != null  && list.size() > 0){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		
		//如果是用户添加案例，设置用户id
		if(0 == type){
			//得到当前用户信息
			Customer record = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
			applicationCase.setUid(record.getId());
		}
		applicationCase.setCreateTime(new  Date());
		applicationCase.setExamine(type);//1管理员，审核状态默认为通过0用户，审核状态为审核中
		return  applicationCaseMapper.insertSelective(applicationCase);
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
	 * @param id
	 * @return
	 */
	public ApplicationCaseDetailsVo findApplicationCaseDetailsMake(Integer id) {
		ApplicationCaseDetailsVo  record = applicationCaseMapper.selectApplicationCaseDetailsMake(id);
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//判断创建用户是否是管理员，如果不是查找用户基本信息
		if(record.getUid() > 0){
			Customer customer = customerService.selectCustomerById(id);
			//用户不存在，也显示案例了，因为没这个需求
			if(customer != null){
				record.setNickname(customer.getNickname());
				record.setHeaderimg(customer.getHeaderimg());
				record.setMobile(customer.getMobile());
			}
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
	 */
	public int alterApplicationCaseByUser(HttpServletRequest request, ApplicationCase applicationCase) {
		//查看案例是否存在
		ApplicationCase app = applicationCaseMapper.findAppCaseById(applicationCase.getId());
		if(app == null || -1 == app.getDel()){
			throw new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//得到当前用户信息
		Customer record = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		//查看用户对该案例有没有未审核的案例，如果有，更新，否则添加
		CaseEdit  caseEdit = caseEditMapper.selectByUidAndAidExamine(record.getId(),app.getId());
		//判断审核状态
		if(caseEdit != null){
			caseEdit.setCreateTime(new  Date());
			caseEdit.setExamine(0);
			caseEdit.setSelectRequirements(applicationCase.getSelectRequirements() == null || applicationCase.getSelectRequirements().trim().equals("")? app.getSelectRequirements():applicationCase.getSelectRequirements());
			caseEdit.setSelectCause(applicationCase.getSelectCause() == null || applicationCase.getSelectCause().trim().equals("")? app.getSelectCause():applicationCase.getSelectCause());
			caseEdit.setSetting(applicationCase.getSetting() == null || applicationCase.getSetting().trim().equals("") ? app.getSetting():applicationCase.getSetting());
			//执行更新操作
			return caseEditMapper.updateByPrimaryKeySelective(caseEdit);
		}else {
			caseEdit = new CaseEdit();
			caseEdit.setAid(app.getId());
			caseEdit.setUid(record.getId());
			caseEdit.setExamine(0);
			caseEdit.setCreateTime(new  Date());
			caseEdit.setSelectRequirements(applicationCase.getSelectRequirements() == null || applicationCase.getSelectRequirements().trim().equals("")? app.getSelectRequirements():applicationCase.getSelectRequirements());
			caseEdit.setSelectCause(applicationCase.getSelectCause() == null || applicationCase.getSelectCause().trim().equals("")? app.getSelectCause():applicationCase.getSelectCause());
			caseEdit.setSetting(applicationCase.getSetting() == null|| applicationCase.getSetting().trim().equals("") ? app.getSetting():applicationCase.getSetting());
			//执行添加操作
			return  caseEditMapper.insertSelective(caseEdit);
		}
		
		
	}

	/**
	 * 根据案例id，包含生产商id，把生产商id映射到ApplicationCase的用户id上
	 */
	@Override
	public ApplicationCase findAppCaseDetailsById(Integer id) {
		return   applicationCaseMapper.selectAppCaseDetailsById(id);
	}

	
	/**
	 * 得到所有案例，条件查询，分页
	 * @param pageIndex
	 * @param pageSize
	 * @param listDto 条件查询
	 * @return
	 */
	public PageInfo findAllApplicationCaseSelective(HttpServletRequest  request,Integer pageIndex, Integer pageSize,
			QueryApplicationCaseListDto listDto) {
		//得到当前用户信息
		Customer record = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		listDto.setUid(record.getId());
		
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
		if(applicationCaseVoList != null  && applicationCaseVoList.size() > 0 ){
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
		}
		redisService.set(hotApplicationCase, JsonUtils.objectToJson(applicationCaseVoList), 30,TimeUnit.DAYS);
		return applicationCaseVoList;
	}

	
	
}
