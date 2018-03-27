package com.zlwon.server.service.impl;

import com.zlwon.rdb.dao.ProgramAccessRecordMapper;
import com.zlwon.rdb.entity.ProgramAccessRecord;
import com.zlwon.server.service.ProgramAccessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 小程序用户访问浏览行为记录ServiceImpl
 * @author yangy
 *
 */

@Service
public class ProgramAccessRecordServiceImpl implements ProgramAccessRecordService {

	@Autowired
	private ProgramAccessRecordMapper programAccessRecordMapper;
	
	/**
	 * 新增小程序用户访问浏览行为记录
	 * @param record
	 * @return
	 */
	@Override
	public int insertProgramAccessRecord(ProgramAccessRecord record){
		int count = programAccessRecordMapper.insert(record);
		return count;
	}
}
