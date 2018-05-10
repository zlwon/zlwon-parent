package com.zlwon.rdb.dao;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.CustomerAuth;
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
	 * 根据用户id和认证类型，得到用户最近提交的审核信息，不管审核状态
	 * @param id 用户id
	 * @param type 认证状态1个人认证6企业认证
	 * @return
	 */
	CustomerApplyInfoVo selectApplyInfoByUid(@Param("id")Integer id, @Param("type")Integer type);
}