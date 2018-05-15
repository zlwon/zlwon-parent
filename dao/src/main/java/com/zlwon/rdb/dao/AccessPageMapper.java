package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.AccessPage;

/**
 * 访问记录统计mapper
 * @author yangy
 *
 */

public interface AccessPageMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(AccessPage record);

    int insertSelective(AccessPage record);

    AccessPage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessPage record);

    int updateByPrimaryKey(AccessPage record);
    
    /**
     * 根据类型更新数量
     * @param type
     * @return
     */
    int updateCountByType(Integer type);
    
    /**
     * 查询所有访问记录
     * @return
     */
    List<AccessPage> selectAllAccessPage();
}