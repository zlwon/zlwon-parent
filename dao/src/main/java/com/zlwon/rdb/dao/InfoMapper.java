package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.Info;

/**
 * 资讯mapper
 * @author yangy
 *
 */

public interface InfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Info record);

    int insertSelective(Info record);

    Info selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Info record);

    int updateByPrimaryKeyWithBLOBs(Info record);

    int updateByPrimaryKey(Info record);
}