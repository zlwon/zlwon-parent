package com.zlwon.server.service;

import com.zlwon.rdb.entity.SysHeader;

/**
 * 系统官网头部Service
 * @author yangy
 *
 */

public interface SysHeaderService {

	/**
     * 根据模块类型查询模块头部信息
     * @param type
     * @return
     */
    SysHeader findSysHeaderByModuleType(Integer type);
}
