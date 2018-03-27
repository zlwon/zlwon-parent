package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.Characteristic;

public interface CharacteristicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Characteristic record);

    int insertSelective(Characteristic record);

    Characteristic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Characteristic record);

    int updateByPrimaryKey(Characteristic record);

    /**
     * 得到所有标签
     * @return
     */
	List<Characteristic> selectAllCharacteristic();
}