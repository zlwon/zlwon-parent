package com.zlwon.server.service;

import com.zlwon.rdb.entity.ProgramAccessRecord;

/**
 * 小程序用户访问浏览行为记录Service
 * @author yangy
 *
 */

public interface ProgramAccessRecordService {

	/**
	 * 新增小程序用户访问浏览行为记录
	 * @param record
	 * @return
	 */
	int insertProgramAccessRecord(ProgramAccessRecord record);
}
