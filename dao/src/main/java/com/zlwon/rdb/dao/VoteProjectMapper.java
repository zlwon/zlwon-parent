package com.zlwon.rdb.dao;

import java.util.List;

import com.zlwon.dto.voteActivity.VoteProjectPageDto;
import com.zlwon.rdb.entity.VoteProject;
import com.zlwon.vo.voteActivity.VoteProjectDetailListVo;
import com.zlwon.vo.voteActivity.VoteProjectDetailVo;

public interface VoteProjectMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(VoteProject record);

    int insertSelective(VoteProject record);

    VoteProject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteProject record);

    int updateByPrimaryKey(VoteProject record);
    
    /**
     * 根据活动ID查询该活动所有参与项目
     * @param form
     * @return
     */
    List<VoteProjectDetailListVo> selectVoteProjectByActivityId(VoteProjectPageDto form);
    
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
     * 根据活动id，得到所有活动项目信息(需要创建用户标记状态为正常)
     * @param aid
     * @return
     */
	List<VoteProjectDetailVo> selectByActivityIdMake(Integer aid);
	
	/**
	 * 根据活动ID查询该活动投票项目数量
	 * @param activityId
	 * @return
	 */
	int countVoteProjectByActivityId(Integer activityId);
}