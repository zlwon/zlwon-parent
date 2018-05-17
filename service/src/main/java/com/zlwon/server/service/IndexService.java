package com.zlwon.server.service;

/**
 * web首页service
 * @author yuand
 *
 */
public interface IndexService {

	/**
	 * web首页未审核的个数统计(label)，
	 * @return
	 */
	Object findIndexNotExamineNumber();

}
