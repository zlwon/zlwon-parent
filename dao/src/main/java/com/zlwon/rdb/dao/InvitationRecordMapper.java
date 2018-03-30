package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.InvitationRecord;
import com.zlwon.vo.invitationRecord.InvitationRecordVo;

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

    /**
	 * 得到所有企业邀请的知料师信息，分页查找
	 * @param pageIndex 
	 * @param pageSize
	 * @param type 邀请接受状态，0邀请确认中，1接受，-1拒绝,null查所有
	 * @return
	 */
	List<InvitationRecordVo> selectAllInvitationRecord(@Param("invitation")Integer invitation);
}