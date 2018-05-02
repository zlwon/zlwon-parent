package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.CharacteristicRecord;

/**
 * 物性标签点赞记录mapper
 * @author yangy
 *
 */

public interface CharacteristicRecordMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(CharacteristicRecord record);

    int insertSelective(CharacteristicRecord record);

    CharacteristicRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CharacteristicRecord record);

    int updateByPrimaryKey(CharacteristicRecord record);
}