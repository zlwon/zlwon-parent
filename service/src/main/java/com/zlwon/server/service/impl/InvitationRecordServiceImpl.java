package com.zlwon.server.service.impl;

import com.zlwon.rdb.dao.InvitationRecordMapper;
import com.zlwon.rdb.entity.InvitationRecord;
import com.zlwon.server.service.InvitationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业账户添加员工账户记录ServiceImpl
 * @author yangy
 *
 */

@Service
public class InvitationRecordServiceImpl implements InvitationRecordService {

	@Autowired
	private InvitationRecordMapper invitationRecordMapper;
	
	/**
     * 根据用户id查询企业账户添加员工账户记录
     * @param uid
     * @return
     */
	@Override
	public List<InvitationRecord> selectInvitationRecordByUid(Integer uid){
		List<InvitationRecord> list = invitationRecordMapper.selectInvitationRecordByUid(uid);
		return list;
	}
}
