package com.zlwon.server.service;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;

/**
 * 用户关注service
 * @author yuand
 *
 */
public interface CustomerAttentionService {

	/**
	 * 用户关注
	 * @param request
	 * @param id 被关注者id
	 * @return
	 */
	int saveCustomerAttention(HttpServletRequest request, Integer id);

	/**
	 * 用户取消关注
	 * @param request
	 * @param id 被关注者id
	 * @return
	 */
	int removeCustomerAttention(HttpServletRequest request, Integer id);

	/**
	 * 得到我关注的人，分页获取
	 * @param request
	 * @return
	 */
	PageInfo findMyAttention(HttpServletRequest request,Integer  pageIndex,Integer  pageSize);

	/**
	 * 得到关注我的人，分页获取
	 * @param request
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageInfo findAttentionMy(HttpServletRequest request, Integer pageIndex, Integer pageSize);

}
