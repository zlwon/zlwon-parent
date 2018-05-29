package com.zlwon.server.service;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.entity.Inform;
import com.zlwon.vo.pc.inform.InformLabelVo;

/**
 * 用户消息service
 * @author yuand
 *
 */
public interface InformService {

	/**
	 * 得到用户所有消息，根据类型查找-分页查找
	 * @param type 0查所有1用户提问审核2用户回答审核3案例编辑审核4用户新增物性标签5材料报价单6用户认证
	 * @return
	 */
	PageInfo findAllInform(HttpServletRequest request,Integer pageIndex, Integer pageSize, Integer type);

	/**
	 * 得到用户未读消息总个数
	 * @param request
	 * @return
	 */
	int findBadgeNumber(HttpServletRequest request);

	/**
	 * 设置消息已读
	 * @param ids 消息id数组
	 * @return
	 */
	int alterInformMakeReadByIds(HttpServletRequest request,String[] ids);

	/**
	 * 得到用户消息个数(label)
	 * @param request
	 * @return
	 */
	InformLabelVo findLabelInformNumber(HttpServletRequest request);

	/**
	 * 新增消息
	 * @param record
	 * @return
	 */
	int insertInform(Inform record);
}
