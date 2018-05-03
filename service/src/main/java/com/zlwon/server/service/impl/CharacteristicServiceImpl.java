package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CharacteristicMapper;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.entity.Characteristic;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.server.service.CharacteristicService;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.characteristic.CharacteristicListVo;

/**
 * 物性表主要特性标签service
 * @author yuand
 *
 */
@Service
public class CharacteristicServiceImpl implements CharacteristicService {

	@Autowired
	private CharacteristicMapper characteristicMapper;
	@Autowired
	private InformMapper  informMapper;

	/**
	 * 得到所有标签，分页获取
	 */
	@Override
	public PageInfo<CharacteristicListVo> findAllCharacteristic(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<CharacteristicListVo> list = characteristicMapper.selectAllCharacteristic();
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
	
	/**
     * 根据物性规格ID查询标签信息
     * @param specId
     * @return
     */
	@Override
	public List<CharacteristicDetailVo> findCharacteristicGroupBySepcId(Integer specId){
		List<CharacteristicDetailVo> list = characteristicMapper.selectCharacteristicGroupBySepcId(specId);
		return list;
    }
	
	/**
     * 根据物性规格ID和用户ID查询标签信息
     * @param specId
     * @param userId
     * @return
     */
	@Override
    public List<CharacteristicDetailVo> findCharacteristicGroupByUserSepcId(Integer specId,Integer userId){
		List<CharacteristicDetailVo> list = characteristicMapper.selectCharacteristicGroupByUserSepcId(specId,userId);
		return list;
    }
	
	/**
    * 根据物性规格ID和标签名称查询标签（已审核）
    * @param specId
    * @param labelName
    * @return
    */
	@Override
   public Characteristic findCharacteristicByNameSpecId(Integer specId,String labelName){
	   Characteristic temp = characteristicMapper.selectCharacteristicByNameSpecId(specId, labelName);
	   return temp;
   }
	
	/**
     * 新增物性标签
     * @param record
     * @return
     */
	@Override
	public int insertCharacteristic(Characteristic record){
    	int count = characteristicMapper.insertSelective(record);
    	return count;
    }

	/**
	 * 标签驳回,添加通知表
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int alterCharacteristicToFailed(Integer id,String  content) {
		Characteristic characteristic = characteristicMapper.selectByPrimaryKey(id);
		if(characteristic == null  || characteristic.getExamine() == 2){
			throw   new  CommonException(characteristic == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_EXAMINE_FAILED);
		}
		//更新为驳回
		characteristic.setExamine(2);
		characteristicMapper.updateByPrimaryKeySelective(characteristic);
		//添加到通知表
		Inform record = new Inform();
		record.setContent(content);
		record.setCreateTime(new  Date());
		record.setIid(characteristic.getId());
		record.setReadStatus((byte) 0);
		record.setStatus((byte) 0);
		record.setType((byte) 4);
		record.setUid(characteristic.getUid());
		return  informMapper.insertSelective(record);
	}

	/**
	 * 得到标签驳回信息
	 * @param id 标签id
	 * @return
	 */
	public String findCharacteristicFailedContent(Integer id) {
		Characteristic characteristic = characteristicMapper.selectByPrimaryKey(id);
		if(characteristic == null  || characteristic.getExamine() != 2){
			throw   new  CommonException(characteristic == null?StatusCode.DATA_NOT_EXIST:StatusCode.DATE_NOT_EXAMINE_FAILED);
		}
		Inform  inform = informMapper.selectCharacteristicFailedByIid(characteristic.getId());
		return inform == null?"":inform.getContent();
	}
	
	
}
