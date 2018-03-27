package com.zlwon.server.service;

import java.util.List;

import com.zlwon.vo.characteristic.CharacteristicDetailVo;

/**
 * 物性规格及标签关联Service
 * @author yangy
 *
 */

public interface CharacteristicSpecMapService {

	/**
     * 根据物性规格ID查询标签信息
     * @param specId
     * @return
     */
    List<CharacteristicDetailVo> selectCharacteristicSpecMapBySepcId(Integer specId);
}
