package com.zlwon.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlwon.rdb.dao.SysHeaderMapper;
import com.zlwon.rdb.entity.SysHeader;
import com.zlwon.server.service.SysHeaderService;

/**
 * 系统官网头部ServiceImpl
 * @author yangy
 *
 */

@Service
public class SysHeaderServiceImpl implements SysHeaderService {

	@Autowired
	private SysHeaderMapper sysHeaderMapper;
	
	/**
     * 根据模块类型查询模块头部信息
     * @param type
     * @return
     */
	@Override
    public SysHeader findSysHeaderByModuleType(Integer type){
		SysHeader temp = sysHeaderMapper.selectSysHeaderByModuleType(type);
		return temp;
    }
}
