package com.zlwon.server.service.impl;

import com.zlwon.rdb.dao.CharacteristicSpecMapMapper;
import com.zlwon.server.service.CharacteristicSpecMapService;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物性规格及标签关联ServiceImpl
 * @author yangy
 *
 */

@Service
public class CharacteristicSpecMapServiceImpl implements CharacteristicSpecMapService {

	@Autowired
	private CharacteristicSpecMapMapper characteristicSpecMapMapper;
	
	/**
     * 根据物性规格ID查询标签信息
     * @param specId
     * @return
     */
	@Override
	public List<CharacteristicDetailVo> selectCharacteristicSpecMapBySepcId(Integer specId){
		List<CharacteristicDetailVo> list = characteristicSpecMapMapper.selectCharacteristicSpecMapBySepcId(specId);
		return list;
	}
}
