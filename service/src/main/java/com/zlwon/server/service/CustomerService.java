package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.Customer;

/**
// * 用户Service
 * @author yangy
 *
 */

public interface CustomerService {

	/**
	 * 根据用户ID查询用户
	 * @param id
	 * @return
	 */
	Customer selectCustomerById(Integer id);
	
	/**
	 * 根据邮箱和密码查询有效用户
	 * @param loginName  邮箱
	 * @param password  密码
	 * @return
	 */
	Customer selectCustomerByNameAndPwd(String loginName, String password);
	
	/**
	 * 根据手机号和密码查询有效用户
	 * @param loginName  手机号
	 * @param password  密码
	 * @return
	 */
	Customer selectCustomerByMobileAndPwd(String loginName, String password);
	
	/**
	 * 根据openId查询用户
	 * @param openId
	 * @return
	 */
	Customer selectCustomerByOpenId(String openId);
	
	/**
	 * 根据用户ID修改openId
	 * @param openId
	 * @param id
	 * @return
	 */
	int updateCustomerOpenIdById(String openId, Integer id);
	
	/**
	 * 根据手机号码查询用户
	 * @param mobile
	 * @return
	 */
	Customer selectCustomerByMobile(String mobile);
	
	/**
	 * 根据邮箱查询用户
	 * @param mail
	 * @return
	 */
	Customer selectCustomerByMail(String mail);
	
	/**
	 * 新增用户信息
	 * @param insertInfo
	 * @return
	 */
	Customer insertCustomer(Customer insertInfo);

	/**
	 * 得到所有正常用户，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo findAllCustomerPage(Integer pageIndex, Integer pageSize);

	/**
	 * 根据用户id，得到用户
	 * @param id
	 * @return
	 */
	Customer findCustomerById(Integer id);

	/**
	 * 新增用户
	 * @param customer
	 * @return
	 */
	int saveCustomerSelective(Customer customer);

	/**
	 * 保存修改后的用户信息，根据用户id，判断用户标记状态
	 * @param id
	 * @return
	 */
	int alterCustomerByIdMake(Customer customer);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	int removeCustomerById(Integer id);
	
	/**
	 * 根据展会ID查询供应商
	 * @param exhibitionId
	 * @return
	 */
	List<Customer> selectManufacturerByExId(Integer exhibitionId);
}
