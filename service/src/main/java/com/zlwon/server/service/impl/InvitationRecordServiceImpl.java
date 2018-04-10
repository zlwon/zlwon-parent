package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.dao.InvitationRecordMapper;
import com.zlwon.rdb.entity.InvitationRecord;
import com.zlwon.server.service.InvitationRecordService;
import com.zlwon.vo.invitationRecord.InvitationRecordVo;

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

	/**
	 * 得到所有企业邀请的知料师信息，分页查找
	 * @param pageIndex 
	 * @param pageSize
	 * @param invitation 邀请接受状态，0邀请确认中，1接受，-1拒绝，null查所有
	 * @return
	 */
	@Override
	public PageInfo<InvitationRecordVo> findAllInvitationRecord(Integer pageIndex, Integer pageSize, Integer invitation) {
		PageHelper.startPage(pageIndex, pageSize);
		List<InvitationRecordVo>  list = invitationRecordMapper.selectAllInvitationRecord(invitation);
		return new PageInfo<InvitationRecordVo>(list);
	}

	/**
	 * 根据邀请id，删除邀请信息
	 */
	@Override
	public int removeInvitationRecordById(Integer id) {
		return invitationRecordMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 得到所有企业邀请的知料师信息，不分页
	 * @param invitation   邀请接受状态，0邀请确认中，1接受，-1拒绝
	 * @return
	 */
	public List<InvitationRecordVo> findAllInvitationRecord(Integer invitation) {
		return invitationRecordMapper.selectAllInvitationRecord(invitation);
	}
}
