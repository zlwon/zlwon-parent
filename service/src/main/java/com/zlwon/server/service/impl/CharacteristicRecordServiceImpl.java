package com.zlwon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.rdb.dao.CharacteristicMapper;
import com.zlwon.rdb.dao.CharacteristicRecordMapper;
import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.rdb.entity.CharacteristicRecord;
import com.zlwon.server.service.CharacteristicRecordService;

/**
 * 物性标签点赞记录ServiceImpl
 * @author yangy
 *
 */

@Service
public class CharacteristicRecordServiceImpl implements CharacteristicRecordService {

	@Autowired
	private CharacteristicMapper characteristicMapper;
	
	@Autowired
	private CharacteristicRecordMapper characteristicRecordMapper;
	
	/**
     * 根据标签ID和用户ID查询标签点赞记录
     * @param characterId
     * @param userId
     * @return
     */
	@Override
    public CharacteristicRecord findCharacteristicRecordByUserCharacterId(Integer characterId,Integer userId){
    	CharacteristicRecord temp = characteristicRecordMapper.selectCharacteristicRecordByUserCharacterId(characterId, userId);
    	return temp;
    }
	
	/**
     * 新增标签点赞记录
     * @param record
     * @return
     */
	@Override
    public int insertCharacteristicRecord(CharacteristicRecord record){
    	
		//新增点赞记录
		int count = characteristicRecordMapper.insertSelective(record);
		
		//更新点赞数
		Characteristic characterInfo = characteristicMapper.selectByPrimaryKey(record.getCharacterId());
		characterInfo.setHot(characterInfo.getHot()+1);
		
		int characterCount = characteristicMapper.updateByPrimaryKeySelective(characterInfo);
		
		return count;
    }
    
    /**
     * 根据ID删除标签点赞记录
     * @param id
     * @return
     */
	@Override
    public int deleteCharacteristicRecordById(Integer id){
    	
		//根据点赞记录查找标签信息
		CharacteristicRecord record = characteristicRecordMapper.selectByPrimaryKey(id);
		
		Characteristic characterInfo = characteristicMapper.selectByPrimaryKey(record.getCharacterId());
		if(characterInfo.getHot() > 0){
			characterInfo.setHot(characterInfo.getHot()-1);
			int characterCount = characteristicMapper.updateByPrimaryKeySelective(characterInfo);
		}else{
			return 0;
		}
		
		//删除点赞记录
		int count = characteristicRecordMapper.deleteByPrimaryKey(id);
		
		return count;
    }
}
