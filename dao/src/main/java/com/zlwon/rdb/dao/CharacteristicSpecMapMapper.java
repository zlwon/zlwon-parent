package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.CharacteristicSpecMap;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;

/**
 * 物性规格及标签关联mapper
 * @author yangy
 *
 */

public interface CharacteristicSpecMapMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(CharacteristicSpecMap record);

    int insertSelective(CharacteristicSpecMap record);

    CharacteristicSpecMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CharacteristicSpecMap record);

    int updateByPrimaryKey(CharacteristicSpecMap record);
    
    /**
     * 根据物性规格ID查询标签信息
     * @param specId
     * @return
     */
    List<CharacteristicDetailVo> selectCharacteristicSpecMapBySepcId(Integer specId);
}