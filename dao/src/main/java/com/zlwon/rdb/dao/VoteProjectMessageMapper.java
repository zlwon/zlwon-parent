package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.VoteProjectMessage;
import com.zlwon.vo.voteActivity.VoteMessageDetailVo;

public interface VoteProjectMessageMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(VoteProjectMessage record);

    int insertSelective(VoteProjectMessage record);

    VoteProjectMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteProjectMessage record);

    int updateByPrimaryKey(VoteProjectMessage record);
    
    /**
     * 根据项目ID集合查询出所有评论
     * @param projectIds
     * @return
     */
    List<VoteMessageDetailVo> selectMessageByProjectIds(String projectIds);

    
    /**
     * 得到所有评论
     * @return
     */
	List<VoteProjectMessage> selectAllVoteProjectMessage(@Param("message") String  message);
	
	/**
	 * 根据活动ID查询该活动总的点评数量
	 * @param activityId
	 * @return
	 */
	int countProjectMessageByActivityId(Integer activityId);
}