package com.zlwon.server.service;

import com.zlwon.rdb.entity.CharacteristicRecord;

/**
 * 物性标签点赞记录Service
 * @author yangy
 *
 */

public interface CharacteristicRecordService {

	/**
     * 根据标签ID和用户ID查询标签点赞记录
     * @param characterId
     * @param userId
     * @return
     */
    CharacteristicRecord findCharacteristicRecordByUserCharacterId(Integer characterId,Integer userId);
    
    /**
     * 新增标签点赞记录
     * @param record
     * @return
     */
    int insertCharacteristicRecord(CharacteristicRecord record);
    
    /**
     * 根据ID删除标签点赞记录
     * @param id
     * @return
     */
    int deleteCharacteristicRecordById(Integer id);
}
