package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.Characteristic;

/**
 * 物性表主要特性标签
 * @author yuand
 *
 */
public interface CharacteristicService {

	/**
	 * 得到所有标签，分页获取
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<Characteristic> findAllCharacteristic(Integer pageIndex, Integer pageSize);

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

}
