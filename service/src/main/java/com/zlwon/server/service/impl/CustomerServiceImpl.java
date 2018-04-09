package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.user.CustomerInfoDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CustomerAttentionMapper;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.CustomerAttention;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	 * 得到所有正常用户，分页查找
	 */
	@Override
	public PageInfo findAllCustomerPage(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<Customer>  list = customerMapper.selectCustomerMake();
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
	 * 新增用户
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
	public CustomerInfoDto findCustomerInfoByIdMake(HttpServletRequest  request,Integer id) {
		//查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix+request.getHeader(token), tokenField, redisService);
		//得到查询用户信息
		CustomerInfoDto record = customerMapper.selectCustomerInfoByIdMake(customer.getId(),id);
		if(record == null){
			throw  new  CommonException(StatusCode.USER_NOT_EXIST);
		}
		return record;
	}

	
	/**
	 * 得到所有用户，根据类型获取，不分页
	 * @param type 账户类型，0普通用户，1知料师，2企业
	 * @return
	 */
	public List<Customer> findCustomerByType(Integer type) {
		return customerMapper.selectCustomerByTypeMake(type);
	}
}
