package com.zlwon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.rdb.dao.SysParamMapper;
import com.zlwon.rdb.entity.SysParam;
import com.zlwon.server.service.SysParamService;

/**
 * 系统参数ServiceImpl
 * @author yangy
 *
 */

@Service
public class SysParamServiceImpl implements SysParamService {

	@Autowired
	private SysParamMapper sysParamMapper;
	
	/**
	 * 根据ID查询系统参数
	 * @param id
	 * @return
	 */
	@Override
	public SysParam findSysParamById(Integer id){
		SysParam temp = sysParamMapper.selectByPrimaryKey(id);
		return temp;
	}
}
