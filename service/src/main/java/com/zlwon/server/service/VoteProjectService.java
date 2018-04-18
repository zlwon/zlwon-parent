package com.zlwon.server.service;

import com.github.pagehelper.PageInfo;
import com.zlwon.dto.voteActivity.VoteProjectPageDto;
import com.zlwon.rdb.entity.VoteProject;
import com.zlwon.vo.voteActivity.VoteProjectDetailListVo;
import com.zlwon.vo.voteActivity.VoteProjectDetailVo;

/**
 * 投票活动参与项目信息Service
 * @author yangy
 *
 */

public interface VoteProjectService {

	/**
	 * 根据id查询投票项目
	 * @param id
	 * @return
	 */
	VoteProject selectVoteProjectById(Integer id);
	
	/**
	 * 根据活动ID分页查询该活动所有参与项目
	 * @param form
	 * @return
	 */
	PageInfo<VoteProjectDetailListVo> selectVoteProjectByActivityId(VoteProjectPageDto form);
	
	/**
     * 根据活动ID查询该活动所有参与项目数量
     * @param form
     * @return
     */
    int selectVoteProjectCountByActivityId(VoteProjectPageDto form);
	
	/**
     * 根据活动项目ID查询活动项目详情
     * @param id
     * @return
     */
	VoteProjectDetailVo selectVoteProjectDetailById(Integer id);

	/**
	 * 根据活动id，得到所有参与的活动项目信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @param aid
	 * @return
	 */
	PageInfo<VoteProjectDetailVo> findAllVoteProjectByAidMake(Integer pageIndex, Integer pageSize, Integer aid);

	/**
	 * 根据参与项目id，修改审核状态，需要先判断参与项目是否存在
	 * @param voteProject
	 * @return
	 */
	int alterVoteProjectExamineById(VoteProject voteProject);

	
	/**
	 * 根据参与项目id，删除参与项目信息
	 * @param id
	 * @return
	 */
	int removeVoteProjectById(Integer id);
	
	/**
	 * 新增投票项目
	 * @param record
	 * @return
	 */
    int insertVoteProject(VoteProject record);
    
    /**
     * 更新投票项目
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(VoteProject record);
    
    /**
	 * 根据活动ID查询该活动投票项目数量
	 * @param activityId
	 * @return
	 */
	int countVoteProjectByActivityId(Integer activityId);
}
