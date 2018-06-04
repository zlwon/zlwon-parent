package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.vo.pc.integration.IntegrationDetailMapVo;

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
    
    /**
     * 根据用户ID查询用户积分异动流水
     * @param userId
     * @return
     */
    List<IntegrationDetailMapVo> selectIntegrationDeatilMapByUserId(Integer userId);
}