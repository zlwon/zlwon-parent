package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.SysHeader;

/**
 * 系统官网头部Mapper
 * @author yangy
 *
 */

public interface SysHeaderMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(SysHeader record);

    int insertSelective(SysHeader record);

    SysHeader selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysHeader record);

    int updateByPrimaryKey(SysHeader record);
    
    /**
     * 根据模块类型查询模块头部信息
     * @param type
     * @return
     */
    SysHeader selectSysHeaderByModuleType(Integer type);
}