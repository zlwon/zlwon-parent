package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.CustomerAuth;
import com.zlwon.vo.customer.CustomerApplyInfoWebVo;
import com.zlwon.vo.pc.customer.CustomerApplyInfoVo;

public interface CustomerAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerAuth record);

    int insertSelective(CustomerAuth record);

    CustomerAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerAuth record);

    int updateByPrimaryKey(CustomerAuth record);

    
    /**
     * 得到用户认证提交信息(审核中状态)，根据用户id(一个用户肯定只有一个审核中的状态)
     * @param uid 用户id
     * @return
     */
	CustomerAuth selectByUIdStatus(Integer uid);

	/**
	 * 根据用户id和认证类型，得到用户最近提交的一个审核信息，不管审核状态
	 * @param id 用户id
	 * @param type 认证状态1个人认证6企业认证
	 * @return
	 */
	CustomerApplyInfoVo selectApplyInfoByUid(@Param("id")Integer id, @Param("type")Integer type);

	/**
	 * 得到所有认证中的用户
	 * @param type 0：查所有1：个人认证6：企业认证
	 * @return
	 */
	List<CustomerApplyInfoWebVo> selectApplyCustomers(@Param("type")Integer type);

	/**
	 * 根据用户id和认证类型，得到用户最近提交的一个审核信息，不管审核状态,如果用户没有申请信息，则返回customer的个人信息
	 * @param id 用户id
	 * @param type 认证状态1个人认证6企业认证
	 * @return
	 */
	CustomerApplyInfoVo selectApplyInfoByUidAndType(@Param("id")Integer id, @Param("type")Integer type);

	/**
	 * 根据认证id，得到 认证详情
	 * @param id 认证id
	 * @return
	 */
	CustomerApplyInfoWebVo selectApplyCustomerById(Integer id);

	/**
	 * 统计未审核的认证个数
	 * @return
	 */
	int selectNotExamineNumber();

	/**
	 * web端首页得到所有认证中的用户，根据认证类型-不分页
	 * 用户信息是认证提交的信息
	 * @param pageSize 显示个数
	 * @param type 0：查所有1：个人认证6：企业认证
	 * @return
	 */
	List<CustomerApplyInfoWebVo> selectNotExamineAuthCustomer(@Param("pageSize")Integer pageSize,@Param("type") Integer type);

	
	/**
	 * 根据用户id，得到该用户认证通过的最新的企业简称id和企业全称id
	 * @param uid 用户id
	 * @return 只有企业简称id和企业全称id
	 */
	CustomerAuth selectOneByUid(Integer uid);
}