package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.DealerProductMap;

/**
 * 经销商可供产品Mapper
 * @author yangy
 *
 */

public interface DealerProductMapMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(DealerProductMap record);

    int insertSelective(DealerProductMap record);

    DealerProductMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DealerProductMap record);

    int updateByPrimaryKey(DealerProductMap record);
}