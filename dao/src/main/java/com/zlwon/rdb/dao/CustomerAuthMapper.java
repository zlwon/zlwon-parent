package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.CustomerAuth;

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
}