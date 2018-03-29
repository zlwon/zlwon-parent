package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.voteActivity.VoteProjectUsersPageDto;
import com.zlwon.rdb.entity.VoteProjectRecord;
import com.zlwon.vo.voteActivity.RecordUserInfoVo;

/**
 * 投票活动参与项目信息点赞记录Service
 * @author yangy
 *
 */

public interface VoteProjectRecordService {

	/**
	 * 新增投票记录（点赞，投票）
	 * @param record
	 * @return
	 */
	int insertVoteProjectRecord(VoteProjectRecord record);
	
	/**
	 * 根据用户ID和项目ID，日期查询用户是否已经参与今日投票
	 * @param uid
	 * @param projectId
	 * @return
	 */
    VoteProjectRecord selectRecordByUidProDate(Integer uid,Integer projectId);
    
    /**
     * 根据投票项目ID查询用户点赞数量统计-分页
     * @param form
     * @return
     */
    PageInfo<RecordUserInfoVo> selectVoteUsersStatics(VoteProjectUsersPageDto form);
    
    /**
	 * 根据活动ID查询该活动总的点赞数量
	 * @param activityId
	 * @return
	 */
	int countProjectRecordByActivityId(Integer activityId);
}
