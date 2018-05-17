package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.Answer;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.vo.pc.inform.InformListVo;

public interface InformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inform record);

    int insertSelective(Inform record);

    Inform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inform record);

    int updateByPrimaryKey(Inform record);

    /**
     * 根据提问信息id，得到驳回提问通知信息
     * @param iid 提问信息id
     * @return
     */
	Inform selectQuestionsFailedByIid(Integer iid);

	 /**
     * 根据回答信息id，得到驳回回答通知信息
     * @param iid 回答信息id
     * @return
     */
	Inform selectAnswerFailedByIid(Integer iid);

	/**
     * 根据编辑案例信息id，得到驳回编辑案例通知信息
     * @param iid 编辑案例信息id
     * @return
     */
	Inform selectApplicationCaseEditFailedByIid(Integer iid);

	/**
     * 根据用户添加物性标签信息id，得到驳回物性标签通知信息
     * @param iid 用户添加物性标签信息id
     * @return
     */
	Inform selectCharacteristicFailedByIid(Integer iid);

	/**
	 * 得到用户所有消息，根据类型获取
	 * @param uid 用户id
	 * @param type 0查所有1用户提问审核2用户回答审核3案例编辑审核4用户新增物性标签5材料报价单6用户认证
	 * @return
	 */
	List<InformListVo> selectAllInformByUid(@Param("uid")Integer uid,@Param("type") Integer type);

	/**
	 * 得到用户未读消息总个数
	 * @param uid 用户id
	 * @return
	 */
	int selectBadgeNumberByUid(Integer uid);

	/**
	 * 设置用户消息为已读
	 * @param uid 用户id
	 * @param ids 消息id数组
	 * @return
	 */
	int updateInformMakeReadByIds(@Param("uid")Integer uid,@Param("ids") String[] ids);

	/**
	 * 得到用户消息个数，可根据类型和是否已读
	 * @param uid 用户id
	 * @param type 0所有1用户提问审核2用户回答审核3案例编辑审核4用户新增物性标签5材料报价单6用户认证
	 * @param readStatus 是否已读0未读1已读，不需要该条件传null
	 * @return
	 */
	int selectInformCountByUidAndType(@Param("uid")Integer uid, @Param("type")Integer type,@Param("readStatus")Integer  readStatus);
}