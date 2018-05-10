package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.Company;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.vo.customer.CustomerDetailVo;
import com.zlwon.vo.pc.customer.CustomerInfoVo;
import com.zlwon.vo.pc.customer.PcCustomerDetailVo;
import com.zlwon.vo.pc.customer.ProducerVo;

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
	 * 根据用户昵称模糊查询用户信息
	 * @param userName
	 * @return
	 */
	List<Customer> selectCustomerByLikeName(@Param("userName") String userName);
	
	/**
	 * 根据用户昵称模糊查询用户信息
	 * 认证用户及企业用户
	 * @param userName
	 * @return
	 */
	List<Customer> selectCustomerByLikeNameLimit(@Param("userName") String userName);
	
	/**
	 * 根据用户ID字符串查询用户信息
	 * @param idStr
	 * @return
	 */
	List<Customer> selectCustomerByidStr(@Param("idStr") String idStr);
	
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
	 * 得到所有正常用户,手机号模糊查询
	 * @return
	 */
	List<Customer> selectCustomerMake(@Param("key")String  key);

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
	 * @param uid 当前用户(关注者)，可能为null，为null就只获取被关注者的信息
	 * @param puid 查询的用户(被关注者)
	 * @return
	 */
	CustomerInfoVo selectCustomerInfoByIdMake(@Param("uid")Integer uid,@Param("puid")Integer puid);

	/**
	 * 得到所有用户，根据类型获取，不分页,只有企业用户才进行模糊查询企业名称
	 * @param type 账户类型，0普通用户，1知料师，2企业
	 * @param key 关键字，只有企业用户才进行模糊查询企业名称
	 * @return
	 */
	List<Customer> selectCustomerByTypeMake(@Param("type")Integer type,@Param("key")String  key);
	
	/**
	 * 根据用户ID查询用户详细信息
	 * @param id
	 * @return
	 */
	PcCustomerDetailVo selectCustomerDetailById(Integer id);

	
	/**
	 * 得到所有生产商，不分页
	 * @return
	 */
	List<ProducerVo> selectProducer();

	/**
	 * 查询用户关联的业务标签内容
	 * @param id
	 * @return
	 */
	List<String> selectCustomerLabelById(Integer id);

	/**
	 * 根据企业简称名称得到存在企业简称(只得到审核通过的)
	 * 	不管是从customer查出的生产商还是company得到企业简称数据，都封装到company类中，因为只是获取id和name和status所属表
	 * @ TODO 后期很有可能报错，
	 * @param companyShortName 企业简称名称
	 */
	Company selectCompanyByShortNameExamine(@Param("companyShortName")String companyShortName);

	
	/**
	 * 得到所有认证通过用户(企业用户或者个人认证用户)，模糊查询
	 * @param keyword 昵称或者手机号
	 * @return
	 */
	List<Customer> selectAllApplyCustomer(@Param("keyword")String keyword);

}
