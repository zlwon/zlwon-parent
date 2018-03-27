package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.Attribute;

/**
 * 物性属性数据
 * @author yuand
 *
 */
public interface AttributeService {

	/**
	 * 得到所有物性属性，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<Attribute> findAllAttribute(Integer pageIndex, Integer pageSize);

	/**
	 * 添加物性属性，字段不判断
	 * @param attribute
	 * @return
	 */
	int saveAttribute(Attribute attribute);

	/**
	 * 根据属性id，得到属性信息
	 * @param id
	 * @return
	 */
	Attribute findAttributeById(Integer id);

	/**
	 * 保存修改后的属性，根据属性id
	 * @param attribute
	 * @return
	 */
	int alterAttributeById(Attribute attribute);

	/**
	 * 根据属性id，删除属性信息
	 * @param id
	 * @return
	 */
	int removeAttributeById(Integer id);

}
