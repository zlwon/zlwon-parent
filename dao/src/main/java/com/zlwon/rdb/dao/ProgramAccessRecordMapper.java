package com.zlwon.rdb.dao;

import com.zlwon.rdb.entity.ProgramAccessRecord;

public interface ProgramAccessRecordMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(ProgramAccessRecord record);

    int insertSelective(ProgramAccessRecord record);

    ProgramAccessRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProgramAccessRecord record);

    int updateByPrimaryKey(ProgramAccessRecord record);
}