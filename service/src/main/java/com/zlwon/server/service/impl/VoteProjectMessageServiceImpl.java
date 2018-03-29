package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.dao.VoteProjectMessageMapper;
import com.zlwon.rdb.entity.VoteProjectMessage;
import com.zlwon.server.service.VoteProjectMessageService;
import com.zlwon.vo.voteActivity.VoteMessageDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 投票活动参与项目信息点评记录ServiceImpl
 * @author yangy
 *
 */

@Service
public class VoteProjectMessageServiceImpl implements VoteProjectMessageService {

	@Autowired
	private VoteProjectMessageMapper voteProjectMessageMapper;
	
	/**
     * 根据项目ID集合查询出所有评论
     * @param projectIds
     * @return
     */
	@Override
	public List<VoteMessageDetailVo> selectMessageByProjectIds(String projectIds){
		List<VoteMessageDetailVo> list = voteProjectMessageMapper.selectMessageByProjectIds(projectIds);
		return list;
	}
	
	/**
     * 新增投票项目评论
     * @param record
     * @return
     */
	@Override
	public int insertVoteProjectMessage(VoteProjectMessage record){
		int count = voteProjectMessageMapper.insert(record);
		return count;
	}

	/**
	 * 得到所有评论，分页查找，
	 */
	@Override
	public PageInfo<VoteProjectMessage> findAllVoteProjectMessage(Integer pageIndex, Integer pageSize,String  message) {
		PageHelper.startPage(pageIndex, pageSize);
		List<VoteProjectMessage>  list = voteProjectMessageMapper.selectAllVoteProjectMessage(message);
		return new PageInfo<VoteProjectMessage>(list);
	}

	
	/**
	 * 删除评论
	 */
	@Override
	public int removeVoteProjectMessageById(Integer id) {
		return voteProjectMessageMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据活动ID查询该活动总的点评数量
	 * @param activityId
	 * @return
	 */
	@Override
	public int countProjectMessageByActivityId(Integer activityId){
		int count = voteProjectMessageMapper.countProjectMessageByActivityId(activityId);
		return count;
	}
}
