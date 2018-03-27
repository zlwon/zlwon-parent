package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.dto.voteActivity.VoteProjectUsersPageDto;
import com.zlwon.rdb.dao.VoteProjectRecordMapper;
import com.zlwon.rdb.entity.VoteProjectRecord;
import com.zlwon.server.service.VoteProjectRecordService;
import com.zlwon.vo.voteActivity.RecordUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 投票活动参与项目信息点赞记录ServiceImpl
 * @author yangy
 *
 */

@Service
public class VoteProjectRecordServiceImpl implements VoteProjectRecordService {

	@Autowired
	private VoteProjectRecordMapper voteProjectRecordMapper;
	
	/**
	 * 新增投票记录（点赞，投票）
	 * @param record
	 * @return
	 */
	@Override
	public int insertVoteProjectRecord(VoteProjectRecord record){
		int count = voteProjectRecordMapper.insert(record);
		return count;
	}
	
	/**
     * 根据用户ID和项目ID，日期查询用户是否已经参与今日投票
     * @param uid
     * @param projectId
     * @return
     */
	@Override
	public VoteProjectRecord selectRecordByUidProDate(Integer uid,Integer projectId){
		VoteProjectRecord temp = voteProjectRecordMapper.selectRecordByUidProDate(uid,projectId);
		return temp;
	}
	
	/**
     * 根据投票项目ID查询用户点赞数量统计-分页
     * @param form
     * @return
     */
	@Override
	public PageInfo<RecordUserInfoVo> selectVoteUsersStatics(VoteProjectUsersPageDto form){
		PageHelper.startPage(form.getCurrentPage(), form.getPageSize());
		List<RecordUserInfoVo> list = voteProjectRecordMapper.selectVoteUsersStatics(form.getProjectId());
		PageInfo<RecordUserInfoVo> info = new PageInfo<>(list);
		
		return info;
	}
}
