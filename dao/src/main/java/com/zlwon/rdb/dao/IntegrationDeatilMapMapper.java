package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.IntegrationDeatilMap;

/**
 * 积分异动明细表mapper
 * @author yangy
 *
 */

public interface IntegrationDeatilMapMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(IntegrationDeatilMap record);

    int insertSelective(IntegrationDeatilMap record);

    IntegrationDeatilMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IntegrationDeatilMap record);

    int updateByPrimaryKey(IntegrationDeatilMap record);
}