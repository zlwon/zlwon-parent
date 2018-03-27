package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.voteActivity.VoteActivityPageDto;
import com.zlwon.rdb.entity.VoteActivity;

public interface VoteActivityMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(VoteActivity record);

    int insertSelective(VoteActivity record);

    VoteActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteActivity record);

    int updateByPrimaryKey(VoteActivity record);

    
    /**
     * 得到所有标记正常的活动
     * @return
     */
	List<VoteActivity> selectAllVoteActivityMake();

	/**
	 * 得到活动标题，得到标记正常的活动
	 * @param title
	 * @return
	 */
	VoteActivity selectByTitleMake(String title);
	
	/**
	 * 查询在活动期限全部投票活动
	 * @param form
	 * @return
	 */
	List<VoteActivity> selectAllVoteActivityDate(VoteActivityPageDto form);
}