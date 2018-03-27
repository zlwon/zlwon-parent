package com.zlwon.server.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.VoteProjectMessage;
import com.zlwon.vo.voteActivity.VoteMessageDetailVo;

/**
 * 投票活动参与项目信息点评记录Service
 * @author yangy
 *
 */

public interface VoteProjectMessageService {

	/**
     * 根据项目ID集合查询出所有评论
     * @param projectIds
     * @return
     */
    List<VoteMessageDetailVo> selectMessageByProjectIds(String projectIds);
    
    /**
     * 新增投票项目评论
     * @param record
     * @return
     */
    int insertVoteProjectMessage(VoteProjectMessage record);

    
    /**
     * 得到所有评论，分页查找，
     * @param pageIndex
     * @param pageSize
     * @return
     */
	PageInfo<VoteProjectMessage> findAllVoteProjectMessage(Integer pageIndex, Integer pageSize,String message);

	
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	int removeVoteProjectMessageById(Integer id);
}
