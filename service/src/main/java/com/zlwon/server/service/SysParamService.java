package com.zlwon.server.service;

import com.zlwon.rdb.entity.SysParam;

/**
 * 系统参数Service
 * @author yangy
 *
 */

public interface SysParamService {

	/**
	 * 根据ID查询系统参数
	 * @param id
	 * @return
	 */
	SysParam findSysParamById(Integer id);
}
