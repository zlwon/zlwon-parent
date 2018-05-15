package com.zlwon.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.pc.customer.ApplyCompanyCustomerDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.CustomerAuth;
import com.zlwon.vo.customer.CustomerApplyInfoWebVo;
import com.zlwon.vo.customer.CustomerDetailVo;
import com.zlwon.vo.pc.customer.CustomerApplyInfoVo;
import com.zlwon.vo.pc.customer.CustomerInfoVo;
import com.zlwon.vo.pc.customer.PcCustomerDetailVo;
import com.zlwon.vo.pc.customer.ProducerVo;

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
	 * 根据用户昵称模糊查询用户信息
	 * @param userName
	 * @return
	 */
	List<Customer> findCustomerByLikeName(String userName);
	
	/**
	 * 根据用户昵称模糊查询用户信息
	 * 认证用户及企业用户
	 * @param userName
	 * @return
	 */
	List<Customer> findCustomerByLikeNameLimit(String userName);
	
	/**
	 * 根据用户ID字符串查询用户信息
	 * @param idStr
	 * @return
	 */
	List<Customer> findCustomerByidStr(String idStr);
	
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
	 * 得到所有正常用户，分页查找，手机号模糊查询
	 * @param pageIndex
	 * @param pageSize
	 * @param key 手机号模糊查询
	 * @return
	 */
	PageInfo findAllCustomerPage(Integer pageIndex, Integer pageSize,String  key);

	/**
	 * 根据用户id，得到用户
	 * @param id
	 * @return
	 */
	Customer findCustomerById(Integer id);

	/**
	 * 后台管理员新增用户
	 * @param customer
	 * @return
	 */
	int saveCustomerSelective(Customer customer);
	
	
	/**
	 * pc用户注册,肯定是普通用户,需要校验验证码
	 * @param customer 只有手机号码和密码
	 * @param code  注册验证码
	 * @return
	 */
	int saveCustomerSelective(Customer customer,String  code);

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
	
	/**
	 * 根据角色查询用户
	 * @param role
	 * @return
	 */
	List<Customer> findCustomerByRole(Integer role);

	/**
	 * 根据用户id，得到用户信息，关注前查询用户信息
	 * @param id
	 * @return
	 */
	CustomerInfoVo findCustomerInfoByIdMake(HttpServletRequest  request,Integer id);

	/**
	 * 得到所有用户，根据类型获取，不分页,只有企业用户才进行模糊查询企业名称
	 * @param type 账户类型，0普通用户，1知料师，2企业
	 * @param key 关键字，只有企业用户才进行模糊查询企业名称
	 * @return
	 */
	List<Customer> findCustomerByType(Integer type,String  key);
	
	/**
	 * 根据用户ID查询用户详细信息
	 * @param id
	 * @return
	 */
	PcCustomerDetailVo findCustomerDetailById(Integer id);
	
	/**
	 * 修改用户信息
	 * @param record
	 * @return
	 */
	int updateCustomer(Customer record);

	/**
	 * 个人中心-应用案例,分页查找
	 * @param request
	 * @return
	 */
	PageInfo findMyApplicationCaseInfo(Integer  pageIndex,Integer  pageSize,HttpServletRequest request);

	
	/**
	 * 得到所有生产商，不分页
	 * @return
	 */
	List<ProducerVo> findProducer();

	/**
	 * 申请成为认证用户(必须上传自己名片)
	 * @return
	 */
	int alter2AuthenticateCustomer(HttpServletRequest request,String  bcard);

	/**
	 * 申请成为企业用户(普通用户和认证用户都可以申请，但是必须是无申请状态下的)
	 * @param request
	 * @param customerDto 提交的企业信息，目前只查看审核通过的企业(不考虑用户提交的企业和正在审核中的企业冲突)
	 * @param customerAuth 提交认证信息，会执行修改用户一些信息，需要保存，后台审核通过后，需要替换用户表中对应的信息
	 *		  customerAuth.type 认证类型1:个人认证6:企业认证
	 * @return
	 */
	int alter2CompanyCustomer(HttpServletRequest request, ApplyCompanyCustomerDto customerDto,CustomerAuth  customerAuth);

	/**
	 * 用户认证-通过用户申请认证信息
	 * 需要判断用户是企业用户还是认证用户，修改后，还要判断pcredis中用户是否存在，存在要修改用户审核状态
	 * @param id 认证id
	 * @return
	 */
	int alterCustomerApplySuccess(Integer id);

	/**
	 * 得到用户认证信息-根据认证状态
	 * 得到用户当前状态认证信息，如果未申请，获取另外状态的，如果另外状态也没有，那就获取个人信息中的数据
	 * @param request
	 * @param type 认证状态1个人认证6企业认证
	 * @return
	 */
	CustomerApplyInfoVo findApplyInfo(HttpServletRequest request, Integer type);

	/**
	 * 得到所有认证中的用户，根据认证类型-分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param type 0：查所有1：个人认证6：企业认证
	 * @return
	 */
	PageInfo findApplyCustomer(Integer pageIndex, Integer pageSize, Integer type);

	
	/**
	 * 用户认证-驳回用户申请认证信息
	 * 需要判断用户是企业用户(把企业认证信息设置为驳回)还是认证用户，修改后，还要判断pcredis中用户是否存在，存在要修改用户审核状态
	 * @param id
	 * @return
	 */
	int alterCustomerApplyFailed(Integer id, String content);

	
	/**
	 * 得到所有认证通过用户(企业用户或者个人认证用户)，模糊查询
	 * @param keyword 昵称或者手机号
	 * @return
	 */
	List<Customer> findAllApplyCustomer(String keyword);

	/**
	 * 根据认证id，得到 认证详情
	 * @param id 认证id
	 * @return
	 */
	CustomerApplyInfoWebVo findApplyCustomerById(Integer id);
}
