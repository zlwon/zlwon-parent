package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CharacteristicMapper;
import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.server.service.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物性表主要特性标签service
 * @author yuand
 *
 */
@Service
public class CharacteristicServiceImpl implements CharacteristicService {

	@Autowired
	private  CharacteristicMapper   characteristicMapper;

	/**
	 * 得到所有标签，分页获取
	 */
	@Override
	public PageInfo<Characteristic> findAllCharacteristic(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<Characteristic> list = characteristicMapper.selectAllCharacteristic();
		return new PageInfo<>(list);
	}

	/**
	 * 更新为审核通过，需要先判断给标签是否存在，然后在判断是否是审核状态
	 */
	@Override
	public int alterCharacteristicToSuccess(Integer id) {
		Characteristic characteristic = characteristicMapper.selectByPrimaryKey(id);
		if(characteristic == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}else if (characteristic.getExamine() == 1) {
			throw  new  CommonException(StatusCode.DATA_IS_EXAMINE);
		}
		Characteristic record = new  Characteristic();
		record.setId(id);
		record.setExamine(1);
		return characteristicMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 删除指定标签，通过标签id
	 */
	@Override
	public int removeCharacteristicById(Integer id) {
		return characteristicMapper.deleteByPrimaryKey(id);
	}
	
	
}
