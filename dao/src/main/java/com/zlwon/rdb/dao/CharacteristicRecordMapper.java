package com.zlwon.rdb.dao;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.CharacteristicRecord;

/**
 * 物性标签点赞记录Mapper
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
    
    /**
     * 根据标签ID和用户ID查询标签点赞记录
     * @param characterId
     * @param userId
     * @return
     */
    CharacteristicRecord selectCharacteristicRecordByUserCharacterId(@Param("characterId") Integer characterId,@Param("userId") Integer userId);
}