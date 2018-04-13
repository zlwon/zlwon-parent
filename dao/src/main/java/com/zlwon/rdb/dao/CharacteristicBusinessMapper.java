package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.CharacteristicBusiness;

/**
 * 个人业务标签mapper
 * @author yangy
 *
 */

public interface CharacteristicBusinessMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(CharacteristicBusiness record);

    int insertSelective(CharacteristicBusiness record);

    CharacteristicBusiness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CharacteristicBusiness record);

    int updateByPrimaryKey(CharacteristicBusiness record);
    
    /**
     * 根据父ID查询个人业务标签
     * @param parentId
     * @return
     */
    List<CharacteristicBusiness> selectCharacteristicBusinessByParentId(Integer parentId);
}