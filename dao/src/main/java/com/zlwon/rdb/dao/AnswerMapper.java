package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.dto.pc.answer.QueryAnswerByQuestionIdDto;
import com.zlwon.dto.pc.answer.QueryMyAnswerByCenterPage;
import com.zlwon.rdb.entity.Answer;
import com.zlwon.vo.answer.AnswerListVo;
import com.zlwon.vo.pc.answer.AnswerDetailVo;
import com.zlwon.vo.pc.answer.AnswerQuestionDetailVo;
import com.zlwon.vo.pc.answer.InvitateAnswerDetailVo;

/**
 * 提问回答Mapper
 * @author yangy
 *
 */

public interface AnswerMapper {

	int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKeyWithBLOBs(Answer record);

    int updateByPrimaryKey(Answer record);
    
    /**
     * 根据问题ID分页查询回答
     * @param form
     * @return
     */
    List<AnswerDetailVo> selectAnswerByquestionId(QueryAnswerByQuestionIdDto form);
    
    /**
     * 分页查询我的回答-个人中心
     * @param form
     * @return
     */
    List<AnswerQuestionDetailVo> selectMyAnswerByCenterPage(QueryMyAnswerByCenterPage form);
    
    /**
     * 查询我的回答数量-个人中心
     * @param userId
     * @return
     */
    int countMyAnswerByCenter(Integer userId);

    /**
	 * 得到所有问答信息，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<AnswerListVo> selectAllAnswerPage();
	
	/**
	 * 根据信息ID和信息类型查询推荐邀请回答用户
	 * @param infoId
	 * @param type
	 * @return
	 */
	List<InvitateAnswerDetailVo> selectInvitateAnswerUserList(@Param("infoId") Integer infoId,@Param("type") Integer type);
	
	/**
	 * 根据搜索模糊查询全部符合条件的搜索用户
	 * @param userName
	 * @return
	 */
	List<InvitateAnswerDetailVo> selectInvitateAnswerUserBySearch(@Param("userName") String userName);
}
