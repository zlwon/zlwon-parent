package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;

/**
 * 物性表主要特性标签Mapper
 * @author yangy
 *
 */

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
	
	/**
	 * 根据物性规格ID查询标签信息
	 * @param specId
	 * @return
	 */
	List<CharacteristicDetailVo> selectCharacteristicGroupBySepcId(Integer specId);
	
	/**
     * 根据物性规格ID和用户ID查询标签信息
     * @param specId
     * @param userId
     * @return
     */
    List<CharacteristicDetailVo> selectCharacteristicGroupByUserSepcId(@Param("specId") Integer specId,@Param("userId") Integer userId);
    
    /**
     * 根据物性规格ID和标签名称查询标签（已审核）
     * @param specId
     * @param labelName
     * @return
     */
    Characteristic selectCharacteristicByNameSpecId(@Param("specId") Integer specId,@Param("labelName") String labelName);
}