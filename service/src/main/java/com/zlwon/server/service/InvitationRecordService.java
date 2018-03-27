package com.zlwon.server.service;

import java.util.List;

import com.zlwon.rdb.entity.InvitationRecord;

/**
 * 企业账户添加员工账户记录Service
 * @author yangy
 *
 */

public interface InvitationRecordService {

	/**
     * 根据用户id查询企业账户添加员工账户记录
     * @param uid
     * @return
     */
    List<InvitationRecord> selectInvitationRecordByUid(Integer uid);
	
}
