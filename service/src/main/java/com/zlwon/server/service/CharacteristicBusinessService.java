package com.zlwon.server.service;

import java.util.List;

import com.zlwon.rdb.entity.CharacteristicBusiness;

/**
 * 个人业务标签Service
 * @author yangy
 *
 */

public interface CharacteristicBusinessService {

	/**
     * 根据父ID查询个人业务标签
     * @param parentId
     * @return
     */
	List<CharacteristicBusiness> findCharacteristicBusinessByParentId(Integer parentId);
}
