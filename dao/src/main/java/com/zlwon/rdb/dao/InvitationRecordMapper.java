package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.rdb.entity.InvitationRecord;

/**
 * 企业账户添加员工账户记录Mapper
 * @author yangy
 *
 */

public interface InvitationRecordMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(InvitationRecord record);

    int insertSelective(InvitationRecord record);

    InvitationRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvitationRecord record);

    int updateByPrimaryKey(InvitationRecord record);
    
    /**
     * 根据用户id查询企业账户添加员工账户记录
     * @param uid
     * @return
     */
    List<InvitationRecord> selectInvitationRecordByUid(Integer uid);
}