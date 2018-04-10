package com.zlwon.rdb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zlwon.rdb.entity.CustomerAttention;
import com.zlwon.vo.pc.customer.CustomerAttentionVo;

public interface CustomerAttentionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerAttention record);

    int insertSelective(CustomerAttention record);

    CustomerAttention selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerAttention record);

    int updateByPrimaryKey(CustomerAttention record);

    /**
     * 根据关注者id和被关注者id，得到信息
     * @param uid 关注者id
     * @param puid 被关注者id
     * @return
     */
	CustomerAttention selectByUidAndPuid(@Param("uid")Integer uid, @Param("puid")Integer puid);

	/**
	 * 得到我关注的人
	 * @param id 当前用户id(关注者)
	 * @return
	 */
	List<CustomerAttentionVo> selectMyAttentionByIdMake(Integer id);

	/**
	 * 得到关注我的人
	 * @param id 当前用户id(被关注者)
	 * @return
	 */
	List<CustomerAttentionVo> selectAttentionMyByIdMake(Integer id);
}