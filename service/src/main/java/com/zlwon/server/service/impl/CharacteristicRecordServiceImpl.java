package com.zlwon.server.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlwon.constant.IntegrationDeatilCode;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CharacteristicMapper;
import com.zlwon.rdb.dao.CharacteristicRecordMapper;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.dao.IntegrationDeatilMapMapper;
import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.rdb.entity.CharacteristicRecord;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
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
	
	@Autowired
	private IntegrationDeatilMapMapper integrationDeatilMapMapper;
	
	@Autowired
	private CustomerMapper customerMapper;
	
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
	@Transactional
    public int insertCharacteristicRecord(CharacteristicRecord record){
    	
		//新增点赞记录
		int count = characteristicRecordMapper.insertSelective(record);
		
		//更新点赞数
		Characteristic characterInfo = characteristicMapper.selectByPrimaryKey(record.getCharacterId());
		characterInfo.setHot(characterInfo.getHot()+1);
		
		int characterCount = characteristicMapper.updateByPrimaryKeySelective(characterInfo);
		
		//增加物性标签创建者积分（非点赞者）
		int upCount = customerMapper.updateIntegrationByUid(characterInfo.getUid(), IntegrationDeatilCode.SPEC_CHARACTERISTIC_LIKE.getNum());
		if(upCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		//新增积分异动明细
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.SPEC_CHARACTERISTIC_LIKE.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.SPEC_CHARACTERISTIC_LIKE.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.SPEC_CHARACTERISTIC_LIKE.getNum());
		interDeatil.setChangeType(1);
		interDeatil.setUid(characterInfo.getUid());
		interDeatil.setCreateTime(new Date());
		
		int igCount = integrationDeatilMapMapper.insertSelective(interDeatil);
		if(igCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
    }
    
    /**
     * 根据ID删除标签点赞记录
     * @param id
     * @return
     */
	@Transactional
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
		
		//减少物性标签创建者积分（非点赞者）
		int upCount = customerMapper.updateIntegrationByUid(characterInfo.getUid(), IntegrationDeatilCode.SPEC_CHARACTERISTIC_CANCEL_LIKE.getNum());
		if(upCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		//新增积分异动明细
		IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
		interDeatil.setType(IntegrationDeatilCode.SPEC_CHARACTERISTIC_CANCEL_LIKE.getCode());
		interDeatil.setDescription(IntegrationDeatilCode.SPEC_CHARACTERISTIC_CANCEL_LIKE.getMessage());
		interDeatil.setIntegrationNum(IntegrationDeatilCode.SPEC_CHARACTERISTIC_CANCEL_LIKE.getNum());
		interDeatil.setChangeType(0);
		interDeatil.setUid(characterInfo.getUid());
		interDeatil.setCreateTime(new Date());
		
		int igCount = integrationDeatilMapMapper.insertSelective(interDeatil);
		if(igCount == 0){
			throw new CommonException(StatusCode.SYS_ERROR);
		}
		
		return count;
    }
}
