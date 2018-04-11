package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.AttributeMapper;
import com.zlwon.rdb.entity.Attribute;
import com.zlwon.server.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物性属性service
 * @author yuand
 *
 */
@Service
public class AttributeServiceImpl implements AttributeService {

	@Autowired
	private  AttributeMapper  attributeMapper;
	
	/**
	 * 根据物性id，得到所有物性属性，分页查找
	 */
	@Override
	public PageInfo<Attribute> findAllAttribute(Integer pageIndex, Integer pageSize,Integer  sid) {
		PageHelper.startPage(pageIndex, pageSize);
		List<Attribute> list = attributeMapper.selectAllAttributeBySid(sid);
		return new  PageInfo<Attribute>(list);
	}

	/**
	 * 添加物性属性
	 */
	@Override
	public int saveAttribute(Attribute attribute) {
		return attributeMapper.insertSelective(attribute);
	}

	/**
	 * 根据属性id，得到属性信息
	 */
	@Override
	public Attribute findAttributeById(Integer id) {
		return attributeMapper.selectByPrimaryKey(id);
	}

	/**
	 *  保存修改后的属性，根据属性id
	 */
	@Override
	public int alterAttributeById(Attribute attribute) {
		//查看该数据是否存在
		Attribute record = attributeMapper.selectByPrimaryKey(attribute.getId());
		if(record == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//执行更新操作，不判断任何字段重复
		return attributeMapper.updateByPrimaryKeySelective(attribute);
	}

	/**
	 * 根据属性id，删除属性信息
	 */
	@Override
	public int removeAttributeById(Integer id) {
		return attributeMapper.deleteByPrimaryKey(id);
	}

}
