package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.Sysadmin;
/**
 * 管理员mapper接口
 * @author yuand
 *
 */
public interface SysadminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sysadmin record);

    int insertSelective(Sysadmin record);

    Sysadmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sysadmin record);

    int updateByPrimaryKey(Sysadmin record);

    /**
     * 根据账号密码查找对象
     * @param usernam
     * @param encode
     * @return
     */
	Sysadmin selectSysadminToLogin(@Param("username")String username, @Param("password")String password);

	/**根据账号查找正常管理员
	 * 一个账号可能存在多次，但是正常状态只有一个
	 * @param username
	 * @return
	 */
	Sysadmin selectByUsernameMake(String username);

	/**
	 * 得到所有正常的管理员
	 * @return
	 */
	List<Sysadmin> selectAllManagerMake();
}