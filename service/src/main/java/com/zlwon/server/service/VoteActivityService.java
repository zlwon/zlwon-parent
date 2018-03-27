package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.voteActivity.VoteActivityPageDto;
import com.zlwon.rdb.entity.VoteActivity;

/**
 * 投票活动Service
 * @author yangy
 *
 */

public interface VoteActivityService {

	/**
	 * 根据id查询投票活动
	 * @param id
	 * @return
	 */
	VoteActivity selectVoteActivityById(Integer id);

	
	/**
	 * 得到所有活动，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo<VoteActivity> findAllVoteActivityMake(Integer pageIndex, Integer pageSize);


	/**
	 * 根据活动id，得到正常的活动信息
	 * @param id
	 * @return
	 */
	VoteActivity findVoteActivityByIdMake(Integer id);


	/**
	 * 添加活动，需要判断标题是否存在，标题唯一
	 * @param voteActivity
	 * @return
	 */
	int saveVoteActivity(VoteActivity voteActivity);

	/**
	 * 更新活动，需要判断活动是否正常存在，然后在判断标题是否存在，标题唯一
	 * @param voteActivity
	 * @return
	 */
	int alterVoteActivityByIdMake(VoteActivity voteActivity);


	/**
	 * 更新标记为删除
	 * @param id
	 * @return
	 */
	int removeVoteActivityById(Integer id);
	
	/**
	 * 查询在活动期限全部投票活动
	 * @param form
	 * @return
	 */
	PageInfo<VoteActivity> selectAllVoteActivityDate(VoteActivityPageDto form);
}
