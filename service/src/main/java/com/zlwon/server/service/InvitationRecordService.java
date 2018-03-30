package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
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

    
    /**
	 * 得到所有企业邀请的知料师信息，分页查找
	 * @param pageIndex 
	 * @param pageSize
	 * @param invitation 邀请接受状态，0邀请确认中，1接受，-1拒绝，null查所有
	 * @return
	 */
	PageInfo findAllInvitationRecord(Integer pageIndex, Integer pageSize, Integer invitation);


	/**
	 * 根据邀请id，删除邀请信息
	 * @param id
	 * @return
	 */
	int removeInvitationRecordById(Integer id);
	
}
