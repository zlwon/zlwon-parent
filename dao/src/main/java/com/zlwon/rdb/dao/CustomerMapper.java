package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.Customer;
import com.zlwon.vo.customer.CustomerDetailVo;
import com.zlwon.vo.pc.customer.CustomerInfoVo;

/**
 * 用户Mapper
 * @author yangy
 *
 */

public interface CustomerMapper {

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
	Customer selectCustomerByNameAndPwd(@Param("loginName")String loginName, @Param("password")String password);
	
	/**
	 * 根据手机号和密码查询有效用户
	 * @param loginName  手机号
	 * @param password  密码
	 * @return
	 */
	Customer selectCustomerByMobileAndPwd(@Param("loginName")String loginName, @Param("password")String password);
	
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
	int updateCustomerOpenIdById(@Param("openId")String openId, @Param("id")Integer id);
	
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
	int insertCustomer(Customer insertInfo);

	
	
	/**
	 * 得到所有正常用户
	 * @return
	 */
	List<Customer> selectCustomerMake();

	/**
	 * 新增用户，非空设值
	 * @param record
	 * @return
	 */
	int insertSelective(Customer record);
	
	/**
	 * 根据用户id，更新用户信息，非空设值
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Customer record);
	
	/**
	 * 根据展会ID查询供应商
	 * @param exhibitionId
	 * @return
	 */
	List<Customer> selectManufacturerByExId(Integer exhibitionId);

	/**
	 * 根据手机号或邮箱找到用户(俩个都是唯一)
	 * @param customer
	 * @return
	 */
	List<Customer> selectByMobileAndEmailMake(Customer customer);
	
	/**
	 * 根据角色查询用户
	 * @param role
	 * @return
	 */
	List<Customer> selectCustomerByRole(Integer role);

	/**
	 * 根据手机号或者邮箱找到用户信息
	 * @param name
	 * @return
	 */
	Customer selectCustomerByMobileOrEmail(String name);

	
	/**
	 * 根据用户id，得到用户信息，关注前查询用户信息（并查看当前用户是否已关注该用户）
	 * @param uid 当前用户(关注者)
	 * @param puid 查询的用户(被关注者)
	 * @return
	 */
	CustomerInfoVo selectCustomerInfoByIdMake(@Param("uid")Integer uid,@Param("puid")Integer puid);

	/**
	 * 得到所有用户，根据类型获取
	 * @param type 账户类型，0普通用户，1知料师，2企业
	 * @return
	 */
	List<Customer> selectCustomerByTypeMake(Integer type);
	
	/**
	 * 根据用户ID查询用户详细信息
	 * @param id
	 * @return
	 */
	CustomerDetailVo selectCustomerDetailById(Integer id);
}
