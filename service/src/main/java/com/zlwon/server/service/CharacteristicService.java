package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.characteristic.CharacteristicListVo;

/**
 * 物性表主要特性标签表Service
 * @author yangy
 *
 */

public interface CharacteristicService {

	/**
	 * 得到所有标签，分页获取
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<CharacteristicListVo> findAllCharacteristic(Integer pageIndex, Integer pageSize);

	/**
	 * 更新为审核通过，需要先判断给标签是否存在，然后在判断是否是审核状态
	 * @param id
	 * @return
	 */
	int alterCharacteristicToSuccess(Integer id);

	
	/**
	 * 删除指定标签，通过标签id
	 * @param id
	 * @return
	 */
	int removeCharacteristicById(Integer id);

	/**
     * 根据物性规格ID查询标签信息
     * @param specId
     * @return
     */
    List<CharacteristicDetailVo> findCharacteristicGroupBySepcId(Integer specId);
    
    /**
     * 根据物性规格ID和用户ID查询标签信息
     * @param specId
     * @param userId
     * @return
     */
    List<CharacteristicDetailVo> findCharacteristicGroupByUserSepcId(Integer specId,Integer userId);
    
    /**
     * 根据物性规格ID和标签名称查询标签（已审核）
     * @param specId
     * @param labelName
     * @return
     */
    Characteristic findCharacteristicByNameSpecId(Integer specId,String labelName);
    
    /**
     * 新增物性标签
     * @param record
     * @return
     */
    int insertCharacteristic(Characteristic record);

    /**
	 * 标签驳回
	 * @param id
	 * @return
	 */
	int alterCharacteristicToFailed(Integer id,String  content);

	/**
	 * 得到标签驳回信息
	 * @param id 标签id
	 * @return
	 */
	String findCharacteristicFailedContent(Integer id);
}
