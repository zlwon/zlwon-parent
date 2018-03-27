package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.VoteProjectRecord;
import com.zlwon.vo.voteActivity.RecordUserInfoVo;

public interface VoteProjectRecordMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(VoteProjectRecord record);

    int insertSelective(VoteProjectRecord record);

    VoteProjectRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteProjectRecord record);

    int updateByPrimaryKey(VoteProjectRecord record);
    
    /**
     * 根据用户ID和项目ID，日期查询用户是否已经参与今日投票
     * @param uid
     * @param projectId
     * @return
     */
    VoteProjectRecord selectRecordByUidProDate(@Param("uid")Integer uid, @Param("projectId")Integer projectId);
    
    /**
     * 根据投票项目ID查询用户点赞数量统计
     * @param projectId
     * @return
     */
    List<RecordUserInfoVo> selectVoteUsersStatics(Integer projectId);
}