package com.zlwon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlwon.rdb.dao.InfoMapper;
import com.zlwon.rdb.entity.Info;
import com.zlwon.server.service.InfoService;

/**
 * 资讯serviceImpl
 * @author yangy
 *
 */

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoMapper infoMapper;
	
	/**
	 * 新增资讯记录
	 * @param record
	 * @return
	 */
	@Override
	public int insertInfo(Info record){
		int count = infoMapper.insertSelective(record);
		return count;
	}
	
	/**
	 * 根据资讯id删除资讯记录
	 * @param id
	 * @return
	 */
	@Override
	public int deleteInfoById(Integer id){
		int count = infoMapper.deleteByPrimaryKey(id);
		return count;
	}
}
