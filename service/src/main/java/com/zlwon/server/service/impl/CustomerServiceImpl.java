package com.zlwon.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.zlwon.dto.pc.customer.ApplyCompanyCustomerDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.CharacteristicBusinessMapper;
import com.zlwon.rdb.dao.CompanyMapper;
import com.zlwon.rdb.dao.CustomerAttentionMapper;
import com.zlwon.rdb.dao.CustomerAuthMapper;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.entity.CharacteristicBusiness;
import com.zlwon.rdb.entity.Company;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.CustomerAuth;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.utils.JsonUtils;
import com.zlwon.utils.MD5Utils;
import com.zlwon.vo.customer.CustomerApplyInfoWebVo;
import com.zlwon.vo.pc.applicationCase.CustomerApplicationCaseVo;
import com.zlwon.vo.pc.customer.CustomerApplyInfoVo;
import com.zlwon.vo.pc.customer.CustomerInfoVo;
import com.zlwon.vo.pc.customer.PcCustomerDetailVo;
import com.zlwon.vo.pc.customer.ProducerVo;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;

/**
 * 用户ServiceImpl
 * @author yangy
 *
 */

@Service
public class CustomerServiceImpl implements CustomerService {

	@Value("${pc.user.header}")
	private  String  token;
	@Value("${pc.redis.user.token.prefix}")
	private  String  tokenPrefix;
	@Value("${pc.redis.user.token.field}")
	private  String  tokenField;
	
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private  RedisService   redisService;
	@Autowired
	private  CustomerAttentionMapper  customerAttentionMapper;
	@Autowired
	private  ApplicationCaseMapper   applicationCaseMapper;
	@Autowired
	private  CompanyMapper   companyMapper;
	@Autowired
	private  CustomerAuthMapper   customerAuthMapper;
	@Autowired
	private  InformMapper   informMapper;
	@Autowired
	private CharacteristicBusinessMapper characteristicBusinessMapper;

	/**
	 * 根据用户ID查询用户
	 * @param id
	 * @return
	 */
	@Override
	public Customer selectCustomerById(Integer id){
		Customer temp = customerMapper.selectCustomerById(id);
		return temp;
	}
	
	/**
	 * 根据用户昵称模糊查询用户信息
	 * @param userName
	 * @return
	 */
	@Override
	public List<Customer> findCustomerByLikeName(String userName){
		List<Customer> list = customerMapper.selectCustomerByLikeName(userName);
		return list;
	}
	
	/**
	 * 根据用户昵称模糊查询用户信息
	 * 认证用户及企业用户
	 * @param userName
	 * @return
	 */
	@Override
	public List<Customer> findCustomerByLikeNameLimit(String userName){
		List<Customer> list = customerMapper.selectCustomerByLikeNameLimit(userName);
		return list;
	}
	
	/**
	 * 根据用户ID字符串查询用户信息
	 * @param idStr
	 * @return
	 */
	@Override
	public List<Customer> findCustomerByidStr(String idStr){
		List<Customer> list = customerMapper.selectCustomerByidStr(idStr);
		return list;
	}
	
	/**
	 * 根据邮箱和密码查询有效用户
	 * @param loginName  邮箱
	 * @param password  密码
	 * @return
	 */
	@Override
	public Customer selectCustomerByNameAndPwd(String loginName, String password){
		Customer temp = customerMapper.selectCustomerByNameAndPwd(loginName, password);
		return temp;
	}
	
	/**
	 * 根据手机号和密码查询有效用户
	 * @param loginName  手机号
	 * @param password  密码
	 * @return
	 */
	@Override
	public Customer selectCustomerByMobileAndPwd(String loginName, String password){
		Customer temp = customerMapper.selectCustomerByMobileAndPwd(loginName, password);
		return temp;
	}
	
	/**
	 * 根据openId查询用户
	 * @param openId
	 * @return
	 */
	@Override
	public Customer selectCustomerByOpenId(String openId){
		Customer temp = customerMapper.selectCustomerByOpenId(openId);
		return temp;
	}
	
	/**
	 * 根据用户ID修改openId
	 * @param openId
	 * @param id
	 * @return
	 */
	@Override
	public int updateCustomerOpenIdById(String openId, Integer id){
		int count = customerMapper.updateCustomerOpenIdById(openId, id);
		return count;
	}
	
	/**
	 * 根据手机号码查询用户
	 * @param mobile
	 * @return
	 */
	@Override
	public Customer selectCustomerByMobile(String mobile){
		Customer temp = customerMapper.selectCustomerByMobile(mobile);
		return temp;
	}
	
	/**
	 * 根据邮箱查询用户
	 * @param mail
	 * @return
	 */
	@Override
	public Customer selectCustomerByMail(String mail){
		Customer temp = customerMapper.selectCustomerByMail(mail);
		return temp;
	}
	
	/**
	 * 新增用户信息
	 * @param insertInfo
	 * @return
	 */
	@Override
	public Customer insertCustomer(Customer insertInfo){
		int count = customerMapper.insertCustomer(insertInfo);
		return insertInfo;
	}

	/**
	 * 得到所有正常用户，分页查找，手机号模糊查询
	 */
	@Override
	public PageInfo findAllCustomerPage(Integer pageIndex, Integer pageSize,String  key) {
		PageHelper.startPage(pageIndex, pageSize);
		List<Customer>  list = customerMapper.selectCustomerMake(key);
		return new PageInfo<Customer>(list);
	}

	/**
	 * 根据用户id，得到用户
	 */
	@Override
	public Customer findCustomerById(Integer id) {
		return customerMapper.selectCustomerById(id);
	}

	/**
	 * 管理员执行新增用户
	 */
	@Override
	public int saveCustomerSelective(Customer customer) {
		Customer  record = null;
		//查看手机号是否重复
		if(StringUtils.isNotBlank(customer.getMobile())){
			record = customerMapper.selectCustomerByMobile(customer.getMobile());
			if(record != null){
				throw  new  CommonException(StatusCode.MOBILE_IS_REGISTER);
			}
		}
		//查看邮箱是否重复
		if(StringUtils.isNotBlank(customer.getEmail())){
			record = customerMapper.selectCustomerByMail(customer.getEmail());
			if(record != null){
				throw  new  CommonException(StatusCode.MAIL_IS_REGISTER);
			}
		}
		
		
		Date  date = new  Date();
		customer.setCreateTime(date);
		customer.setPassword(MD5Utils.encode(customer.getPassword()));
		//如果是知料师
		if(customer.getRole()==1){
			customer.setApplyTime(date);//知料师申请日期
		}
		
		return  customerMapper.insertSelective(customer);
	}
	
	
	/**
	 * pc用户注册,肯定是普通用户,需要校验验证码
	 * @param customer 只有手机号码和密码
	 * @param code  注册验证码
	 * @return
	 */
	@Override
	public int saveCustomerSelective(Customer customer,String  code) {
		Customer  record = null;
		//查看手机号是否重复
		if(StringUtils.isNotBlank(customer.getMobile())){
			record = customerMapper.selectCustomerByMobile(customer.getMobile());
			if(record != null){
				throw  new  CommonException(StatusCode.MOBILE_IS_REGISTER);
			}
		}
		//校验验证码
		String recode = redisService.get("mobilecode_" + customer.getMobile());
		if (StringUtils.isBlank(recode) || !recode.equals(code)) {
			throw new CommonException(
					StringUtils.isBlank(recode) ? StatusCode.ACTIVE_CODE_EXPIRED : StatusCode.ACTIVE_CODE_INVALID);
		}
		redisService.delete("mobilecode_" + customer.getMobile());
		
		Date  date = new  Date();
		customer.setCreateTime(date);
		customer.setPassword(MD5Utils.encode(customer.getPassword()));
		customer.setRole(0);//设置用户类型为普通用户 
		
		String randomStr = String.valueOf((int)((Math.random()*9+1)*10));
		customer.setNickname("知料用户"+customer.getMobile().substring(3)+randomStr);
		
		return  customerMapper.insertSelective(customer);
	}

	/**
	 * 保存修改后的用户信息，根据用户id，判断用户标记状态
	 */
	@Override
	public int alterCustomerByIdMake(Customer customer) {
		Customer record = customerMapper.selectCustomerById(customer.getId());
		if(record==null  || record.getDel() != 1){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//查看手机号是否重复
		if(StringUtils.isNotBlank(customer.getMobile())){
			record = customerMapper.selectCustomerByMobile(customer.getMobile());
			if(record != null && record.getId() != customer.getId()){
				throw  new  CommonException(StatusCode.MOBILE_IS_REGISTER);
			}
		}
		//查看邮箱是否重复
		if(StringUtils.isNotBlank(customer.getEmail())){
			record = customerMapper.selectCustomerByMail(customer.getEmail());
			if(record != null && record.getId() != customer.getId()){
				throw  new  CommonException(StatusCode.MAIL_IS_REGISTER);
			}
		}
		
		//执行更新操作
		return  customerMapper.updateByPrimaryKeySelective(customer);
	}

	/**
	 * 删除用户
	 */
	@Override
	public int removeCustomerById(Integer id) {
		Customer record = new Customer();
		record.setId(id);
		record.setDel(-1);
		return customerMapper.updateByPrimaryKeySelective(record );
	}
	
	/**
	 * 根据展会ID查询供应商
	 * @param exhibitionId
	 * @return
	 */
	@Override
	public List<Customer> selectManufacturerByExId(Integer exhibitionId){
		List<Customer> list = customerMapper.selectManufacturerByExId(exhibitionId);
		return list;
	}
	
	/**
	 * 根据角色查询用户
	 * @param role
	 * @return
	 */
	@Override
	public List<Customer> findCustomerByRole(Integer role){
		List<Customer> list = customerMapper.selectCustomerByRole(role);
		return list;
	}

	
	/**
	 * 根据查询用户id，得到查询用户信息，关注前查询用户信息
	 * @param id
	 * @return
	 */
	public CustomerInfoVo findCustomerInfoByIdMake(HttpServletRequest  request,Integer id) {
		//查看当前用户信息(关注者)
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		//得到查询用户(被关注者)信息
		CustomerInfoVo record = customerMapper.selectCustomerInfoByIdMake(customer == null?null:customer.getId(),id);
		if(record == null){
			throw  new  CommonException(StatusCode.USER_NOT_EXIST);
		}
		//查询用户(被关注者)关联的业务标签
		List<String>  list = customerMapper.selectCustomerLabelById(id);
		record.setLabel(list.isEmpty() || (list.size() == 1  && list.get(0) == null)?new ArrayList<String>():list);
		return record;
	}

	/**
	 * 得到所有用户，根据类型获取，不分页,只有企业用户才进行模糊查询企业名称
	 * @param type 账户类型，0普通用户，1知料师，2企业
	 * @param key 关键字，只有企业用户才进行模糊查询企业名称
	 * @return
	 */
	public List<Customer> findCustomerByType(Integer type,String  key) {
		return customerMapper.selectCustomerByTypeMake(type,key);
	}
	
	/**
	 * 根据用户ID查询用户详细信息
	 * @param id
	 * @return
	 */
	@Override
	public PcCustomerDetailVo findCustomerDetailById(Integer id){
		PcCustomerDetailVo temp = customerMapper.selectCustomerDetailById(id);
		return temp;
	}
	
	/**
	 * 修改用户信息
	 * @param record
	 * @return
	 */
	@Override
	public int updateCustomer(Customer record){
		int count = customerMapper.updateByPrimaryKeySelective(record);
		return count;
	}

	/**
	 * 个人中心-应用案例，分页查找
	 * @param request
	 * @return
	 */
	public PageInfo findMyApplicationCaseInfo(Integer  pageIndex,Integer  pageSize,HttpServletRequest request) {
		//查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		//查询该用户提交的案例信息和编辑过的案例信息
		PageHelper.startPage(pageIndex, pageSize);
		List<CustomerApplicationCaseVo> list = applicationCaseMapper.selectMyApplicationCaseInfo(customer.getId());
		return new  PageInfo<CustomerApplicationCaseVo>(list);
	}

	/**
	 * 得到所有生产商，不分页
	 * @return
	 */
	public List<ProducerVo> findProducer() {
		return customerMapper.selectProducer();
	}

	/**
	 * 申请成为认证用户(必须上传自己名片),只有普通用户才可以申请
	 * 已作废
	 * @return
	 */
	@Transactional
	public int alter2AuthenticateCustomer(HttpServletRequest request,String  bcard) {
		//查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		//查看用户是否有未审核的状态
		//查看是否有未审核的申请认证用户状态
		if(customer.getRoleApply() != -1){
			throw  new  CommonException(StatusCode.EXIST_APPLY_STATUS); //有未审核的申请状态
		}
		if(customer.getRole() != 0){
			throw  new  CommonException(StatusCode.NOT_APPLY_STATUS);//不是普通用户
		}
		
		//保存用户名片信息，同步redis，修改审核状态
		customer.setRoleApply(1);
		customer.setBcard(bcard);
		customer.setApply(1);
		customer.setApplyTime(new  Date());
		CustomerUtil.resetCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, JsonUtils.objectToJson(customer), redisService);
		return customerMapper.updateByPrimaryKeySelective(customer);
	}

	/**
	 * 申请成为企业用户(普通用户和认证用户都可以申请，但是必须是无申请状态下的)
	 * 如果用户添加的企业全称不存在，该用户就是申请成为企业用户，否则就是认证用户，只是关联企业或添加企业名称
	 * @param request
	 * @param customerDto 提交的企业信息，目前只查看审核通过的企业(不考虑用户提交的企业和正在审核中的企业冲突)
	 * @param customerAuth 提交认证信息，会执行修改用户一些信息，需要保存，后台审核通过后，需要替换用户表中对应的信息
	 *		  customerAuth.type 认证类型1:个人认证6:企业认证
	 * @return
	 */
	@Transactional
	public int alter2CompanyCustomer(HttpServletRequest request, ApplyCompanyCustomerDto customerDto,CustomerAuth  customerAuth) {
		//查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		//查看用户是否有未审核的状态
		//查看是否有未审核的申请认证用户状态
		if(customer.getRoleApply() != -1){
			throw  new  CommonException(StatusCode.EXIST_APPLY_STATUS); //有未审核的申请状态
		}
		if(customer.getRole() == 6){
			throw  new  CommonException(StatusCode.DATE_EXAMINE_SUCCESS);//已是企业用户,只要是企业用户，就不可以认证了
		}
		if(StringUtils.isBlank(customerDto.getCompanyFullName()) || StringUtils.isBlank(customerDto.getCompanyShortName())){
			throw  new  CommonException(StatusCode.INVALID_PARAM);
		}
		
		
		//根据企业简称名称查看是否存在企业简称(只得到审核通过的和type是企业状态的)
		Company  company = customerMapper.selectCompanyByShortNameExamine(customerDto.getCompanyShortName());
		boolean  flag = true;//标记企业全称是否存在，true存在，false不存在
		int   status = 0;//企业全称所属哪张表(0用户表customer，1company表)
		Company fullCompany = null;
		Date date = new  Date();
		if(company == null){
			company = new  Company();
			//把企业简称添加到company
			company.setName(customerDto.getCompanyShortName());
			company.setStatus((byte) 1);
			company.setUid(customer.getId());
			company.setExamine((byte) 0);
			company.setCreateTime(date);
			//设置企业简称是那种类型添加的
			company.setType(customerAuth.getType());
			companyMapper.insertSelective(company);
			flag = false;
			status = 1;
		}else {
			//根据企业全称和父id(企业简称)，和所属表标识，得到企业全称数据(只得到审核通过的)
			fullCompany = companyMapper.selectCompanyByFullNameExamine(customerDto.getCompanyFullName(),company.getId(),company.getStatus());
			if(fullCompany == null){
				//把企业全称添加到company
				flag = false;
				status = company.getStatus();
			}
		}
		
		
		//判断用户是执行什么认证1个人认证6企业认证
		if(customerAuth.getType() == 1){//个人认证，企业存在就是关联，否则就是添加企业信息(简称或全称)
		}else if (customerAuth.getType() == 6) {//企业认证，企业全称必须不存在
			if(flag){
				throw   new CommonException(StatusCode.DATA_IS_EXIST);
			}
		}else {
			throw   new CommonException(StatusCode.INVALID_PARAM);
		}
		
		
		if(!flag){
			//把企业全称添加到company
			fullCompany = new  Company();
			try {
				BeanUtils.copyProperties(fullCompany,customerDto);
			} catch (Exception e) {
				throw  new  CommonException(e);
			} 
			fullCompany.setParentId(company.getId());
			fullCompany.setName(customerDto.getCompanyFullName());
			fullCompany.setStatus((byte) 1);
			fullCompany.setUid(customer.getId());
			fullCompany.setExamine((byte) 0);
			fullCompany.setCreateTime(date);
			//设置企业全称是那种类型添加的
			fullCompany.setType(customerAuth.getType());
			fullCompany.setStatus((byte) status);
			companyMapper.insertSelective(fullCompany);
		}
		
		//修改用户认证信息
		customer.setApply(1);
		customer.setApplyTime(new  Date());
		//设置用户认证的状态1个人认证6企业认证
		customer.setRoleApply(Integer.valueOf(customerAuth.getType()));
		int num = customerMapper.updateByPrimaryKeySelective(customer);
		
		//保存用户提交的信息到记录表中
		customerAuth.setUid(customer.getId());
		customerAuth.setCreateTime(date);
		customerAuth.setStatus((byte) 0);
		customerAuth.setShortcompanyId(company.getId());//设置企业简称id
		customerAuth.setFullcompanyId(fullCompany.getId());//设置企业全称id
		customerAuthMapper.insertSelective(customerAuth);
		
		//更新到redis中
		CustomerUtil.resetCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, JsonUtils.objectToJson(customer), redisService);
		return  num;
		 
	}

	/**
	 * 用户认证-通过用户申请认证信息
	 * 需要判断用户是企业用户还是认证用户，修改后，还要判断pcredis中用户是否存在，存在要修改用户审核状态
	 * @param id 认证id
	 * @return
	 */
	@Transactional
	public int alterCustomerApplySuccess(Integer id) {
		//通过认证id，得到认证信息
		CustomerAuth  customerAuth = customerAuthMapper.selectByPrimaryKey(id);
		if(customerAuth == null || customerAuth.getStatus() != 0){
			throw   new  CommonException(customerAuth == null?StatusCode.DATA_NOT_EXIST:StatusCode.NOT_EXAMINE_STATUS);
		}
		
		
		//得到认证用户
		Customer customer = customerMapper.selectCustomerById(customerAuth.getUid());
		if(customer == null  ||  customer.getDel() != 1  ||  customer.getRoleApply() == -1){
			throw  new  CommonException((customer == null  ||  customer.getDel() != 1) ? StatusCode.DATA_NOT_EXIST:StatusCode.NOT_EXAMINE_STATUS);
		}
		Date  date = new  Date();
		
		
		customer.setNickname(customerAuth.getNickname());
		customer.setEmail(customerAuth.getEmail());
		customer.setOccupation(customerAuth.getOccupation());
		customer.setLabel(customerAuth.getLabel());
		customer.setBcard(customerAuth.getBcard());
		customer.setMyinfo(customerAuth.getMyinfo());
		customer.setCompanyId(customerAuth.getFullcompanyId());
		customerAuth.setAuditTime(date);
		customerAuth.setStatus((byte) 1);
		customerAuthMapper.updateByPrimaryKeySelective(customerAuth);
		
		
		//1得到该企业用户提交的企业全称信息，匹配是否该全称(还需要父id)存在已审核通过的信息，不存在：设置为审核通过，存在：不做处理，返回前端报错信息
		Company companyFull = companyMapper.selectByPrimaryKey(customerAuth.getFullcompanyId());//根据用户提交认证关联的企业全称id，得到企业全称信息
		if(companyFull == null){
			throw   new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
//		if(customer.getRoleApply() == 1){//1用户申请认证用户
//			customer.setRole(1);//1认证用户6企业用户
//		}else if (customer.getRoleApply() == 6) {//用户申请企业用户，需要把企业信息修改为审核通过，而且需要修改类型(type)为6
			
			
			//根据企业全称名称匹配审核通过的企业全称信息
			Company companySuccess = companyMapper.selectCompanyByFullNameExamine(companyFull.getName(), companyFull.getParentId(), companyFull.getStatus());
			if(companySuccess != null  &&  !companySuccess.getId().equals(companyFull.getId())){
				throw  new  CommonException(StatusCode.DATA_IS_EXIST);
			}
			//修改企业全称审核通过
			if(companyFull.getExamine() != 1){
				companyFull.setAuditTime(date);
				companyFull.setExamine((byte) 1);
				companyFull.setType(customerAuth.getType());
				companyMapper.updateByPrimaryKeySelective(companyFull);
			}
			
			
			
			
			//2判断该企业全称的父类是customer生产商(不做判断)的还是company的企业简称
			if(companyFull.getStatus() == 1){
				//根据企业全称父id，得到企业简称信息，如果企业简称是审核中状态需要判断简称名称是否存在审核通过的
				Company companyShort = companyMapper.selectByPrimaryKey(companyFull.getParentId());
				if(companyShort == null  ||  companyShort.getParentId() != 0){
					throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
				}
				//查看企业简称是否存在已审核通过的(只得到审核通过和type是企业状态的)
				Company re = companyMapper.selectApplySuccessShortCompanyByShortName(companyShort.getName());
				if(re != null  &&  !re.getId().equals(companyShort.getId())){
					throw  new  CommonException(StatusCode.DATA_IS_EXIST);
				}
				
				//修改企业简称审核通过
				if(companyShort.getExamine() != 1){
					companyShort.setAuditTime(date);
					companyShort.setExamine((byte) 1);
					companyShort.setType(customerAuth.getType());
					companyMapper.updateByPrimaryKeySelective(companyShort);
				}
			}
			
//		}
		
		//企业简称名称，更新用户公司名称
		String  companyShortName = companyMapper.selectShortCompanyNameByIdStatus(customerAuth.getFullcompanyId(),companyFull.getStatus());
		
		
		//修改用户信息
		customer.setRole(Integer.valueOf(customerAuth.getType()));//1认证用户6企业用户
		customer.setApplyTime(date);//审核日期
		customer.setRoleApply(-1);//申请成为的类型
		customer.setApply(2);//审核通过
		customer.setCompany(companyShortName);//设置企业名称，企业简称的名称
		int num = customerMapper.updateByPrimaryKeySelective(customer);
		
		//发送通知
		Inform inform = new  Inform();
		inform.setIid(customerAuth.getId());
		inform.setCreateTime(date);
		inform.setReadStatus((byte) 0);
		inform.setStatus((byte) 1);
		inform.setType((byte) 6);
		inform.setUid(customer.getId());
		informMapper.insertSelective(inform);
		
		
		//查看pc用户是否登录，登录修改redis用户信息
		String token = MD5Utils.encode(customer.getId() + "");//token值
		String info = (String)redisService.hGet(tokenPrefix+token, tokenField);
		if(StringUtils.isNotBlank(info)){//用户是登录状态，
			redisService.hSet(tokenPrefix+token, tokenField, JsonUtils.objectToJson(customer));
		}
		return  num;
	}
	
	
	/**
	 * 用户认证-驳回用户申请认证信息
	 * 需要判断用户是企业用户(把企业认证信息设置为驳回)还是认证用户，修改后，还要判断pcredis中用户是否存在，存在要修改用户审核状态
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int alterCustomerApplyFailed(Integer id, String content) {
		//通过认证id，得到认证信息
		CustomerAuth  customerAuth = customerAuthMapper.selectByPrimaryKey(id);
		if(customerAuth == null || customerAuth.getStatus() != 0){
			throw   new  CommonException(customerAuth == null?StatusCode.DATA_NOT_EXIST:StatusCode.NOT_EXAMINE_STATUS);
		}
		
		
		//得到认证用户
		Customer customer = customerMapper.selectCustomerById(customerAuth.getUid());
		if(customer == null  ||  customer.getDel() != 1  ||  customer.getRoleApply() == -1){
			throw  new  CommonException((customer == null  ||  customer.getDel() != 1) ? StatusCode.DATA_NOT_EXIST:StatusCode.NOT_EXAMINE_STATUS);
		}
		Date  date = new  Date();
		
		//设置认证信息审核驳回
		customerAuth.setAuditTime(date);
		customerAuth.setStatus((byte) 2);
		customerAuthMapper.updateByPrimaryKeySelective(customerAuth);
		
		
		if(customer.getRoleApply() == 6){//1用户申请企业认证，需要修改企业信息为驳回，如果用户申请个人认证，只需要修改认证信息为驳回就行
			//1得到该企业用户提交的企业全称信息,只有审核中才可以驳回
			Company companyFull = companyMapper.selectByPrimaryKey(customerAuth.getFullcompanyId());//根据用户提交认证关联的企业全称id，得到企业全称信息
			if(companyFull == null || companyFull.getExamine() != 0){
				throw   new  CommonException(companyFull == null?StatusCode.DATA_NOT_EXIST:StatusCode.NOT_EXAMINE_STATUS);
			}
			
			//修改企业全称审核驳回，因为是企业用户，所以企业全称肯定是该用户创建的，是未审核状态
			companyFull.setAuditTime(date);
			companyFull.setExamine((byte) 2);
			companyMapper.updateByPrimaryKeySelective(companyFull);
			
			
			//2判断该企业全称的父类是customer生产商(不做判断)的还是company的企业简称,如果企业简称不是通过，需要设置为驳回
			if(companyFull.getStatus() == 1){
				//根据企业全称父id，得到企业简称信息
				Company companyShort = companyMapper.selectByPrimaryKey(companyFull.getParentId());
				if(companyShort == null){
					throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
				}
				
				//修改企业简称审核驳回
				if(companyShort.getExamine() == 0){
					companyShort.setAuditTime(date);
					companyShort.setExamine((byte) 2);
					companyMapper.updateByPrimaryKeySelective(companyShort);
				}
			}
		}
		
		//修改用户信息
		customer.setApplyTime(date);//审核日期
		customer.setRoleApply(-1);//申请成为的类型
		customer.setApply(0);//TODO 审核正常,不知道这个字段有没有做用，应该是修改为审核失败的
		int num = customerMapper.updateByPrimaryKeySelective(customer);
		
		//发送通知
		Inform inform = new  Inform();
		inform.setIid(customerAuth.getId());
		inform.setContent(content);
		inform.setCreateTime(date);
		inform.setReadStatus((byte) 0);
		inform.setStatus((byte) 0);
		inform.setType((byte) 6);
		inform.setUid(customer.getId());
		informMapper.insertSelective(inform);
		
		
		//查看pc用户是否登录，登录修改redis用户信息
		String token = MD5Utils.encode(customer.getId() + "");//token值
		String info = (String)redisService.hGet(tokenPrefix+token, tokenField);
		if(StringUtils.isNotBlank(info)){//用户是登录状态，
			redisService.hSet(tokenPrefix+token, tokenField, JsonUtils.objectToJson(customer));
		}
		return  num;

	}
	

	/**
	 * 得到用户认证信息-根据认证状态
	 * 得到用户当前状态认证信息，如果未申请，获取另外状态的，如果另外状态也没有，那就获取个人信息中的数据
	 * @param request
	 * @param type 认证状态1个人认证6企业认证
	 * @return
	 */
	@Override
	public CustomerApplyInfoVo findApplyInfo(HttpServletRequest request, Integer type) {
		//查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		CustomerApplyInfoVo infoVo = customerAuthMapper.selectApplyInfoByUid(customer.getId(),type);
		if(infoVo == null){
			//获取另外状态的，如果另外状态也没有，那就获取个人信息中的数据
			infoVo = customerAuthMapper.selectApplyInfoByUidAndType(customer.getId(),type == 1?6:1);
		}
		List<CharacteristicBusiness> list = characteristicBusinessMapper.selectCharacteristicBusinessByIdStr(StringUtils.isBlank(infoVo.getLabel())?"0":infoVo.getLabel());
		infoVo.setCharacterList(list);
		return infoVo;
	}

	/**
	 * 得到所有认证中的用户，根据认证类型-分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param type 0：查所有1：个人认证6：企业认证
	 * @return
	 */
	public PageInfo findApplyCustomer(Integer pageIndex, Integer pageSize, Integer type) {
		PageHelper.startPage(pageIndex, pageSize);
		List<CustomerApplyInfoWebVo>  list = customerAuthMapper.selectApplyCustomers(type);
		if(list != null  && list.size()>0){
			for (CustomerApplyInfoWebVo customerApplyInfoWebVo : list) {
				customerApplyInfoWebVo.setCharacterList(characteristicBusinessMapper.selectCharacteristicBusinessByIdStr(customerApplyInfoWebVo.getLabel()));
			}
		}
		return new  PageInfo<>(list);
	}

	
	/**
	 * 得到所有认证通过用户(企业用户或者个人认证用户)，模糊查询
	 * @param keyword 昵称或者手机号
	 * @return
	 */
	public List<Customer> findAllApplyCustomer(String keyword) {
		List<Customer>  list = customerMapper.selectAllApplyCustomer(keyword);
		return list;
	}

	/**
	 * 根据认证id，得到 认证详情
	 * @param id 认证id
	 * @return
	 */
	@Override
	public CustomerApplyInfoWebVo findApplyCustomerById(Integer id) {
		CustomerApplyInfoWebVo  vo = customerAuthMapper.selectApplyCustomerById(id);
		if(vo == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		vo.setCharacterList(characteristicBusinessMapper.selectCharacteristicBusinessByIdStr(vo.getLabel()));
		return vo;
	}

	/**
	 * web端首页得到所有认证中的用户，根据认证类型-不分页
	 * 用户信息是认证提交的信息
	 * @param pageSize 显示个数
	 * @param type 0：查所有1：个人认证6：企业认证
	 * @return
	 */
	@Override
	public List<CustomerApplyInfoWebVo> findNotExamineAuthCustomer(Integer pageSize, Integer type) {
		List<CustomerApplyInfoWebVo>  list = customerAuthMapper.selectNotExamineAuthCustomer(pageSize,type);
		if(list != null  && list.size()>0){
			for (CustomerApplyInfoWebVo customerApplyInfoWebVo : list) {
				customerApplyInfoWebVo.setCharacterList(characteristicBusinessMapper.selectCharacteristicBusinessByIdStr(customerApplyInfoWebVo.getLabel()));
			}
		}
		return list;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
