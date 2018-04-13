package com.zlwon.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.rdb.dao.CharacteristicBusinessMapper;
import com.zlwon.rdb.entity.CharacteristicBusiness;
import com.zlwon.server.service.CharacteristicBusinessService;

/**
 * 个人业务标签ServiceImpl
 * @author yangy
 *
 */

@Service
public class CharacteristicBusinessServiceImpl implements CharacteristicBusinessService {

	@Autowired
	private CharacteristicBusinessMapper characteristicBusinessMapper;
	
	/**
     * 根据父ID查询个人业务标签
     * @param parentId
     * @return
     */
	@Override
    public List<CharacteristicBusiness> findCharacteristicBusinessByParentId(Integer parentId){
		List<CharacteristicBusiness> list = characteristicBusinessMapper.selectCharacteristicBusinessByParentId(parentId);
		return list;
    }
}
