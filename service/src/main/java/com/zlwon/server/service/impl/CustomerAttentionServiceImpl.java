package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.user.CustomerAttentionDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CustomerAttentionMapper;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.CustomerAttention;
import com.zlwon.server.service.CustomerAttentionService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CustomerUtil;

/**
 * 用户关注
 * 
 * @author yuand
 *
 */
@Service
public class CustomerAttentionServiceImpl implements CustomerAttentionService {

	@Value("${pc.user.header}")
	private String token;
	@Value("${pc.redis.user.token.prefix}")
	private String tokenPrefix;
	@Value("${pc.redis.user.token.field}")
	private String tokenField;

	@Autowired
	private CustomerAttentionMapper customerAttentionMapper;
	@Autowired
	private RedisService redisService;

	/**
	 * 用户关注
	 * 
	 * @param request
	 * @param id
	 *            被关注者id
	 * @return
	 */
	public int saveCustomerAttention(HttpServletRequest request, Integer id) {
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField,
				redisService);
		// 查看当前用户是否已关注被关注者
		CustomerAttention attention = customerAttentionMapper.selectByUidAndPuid(customer.getId(), id);
		if (attention != null) {
			throw new CommonException(StatusCode.ATTENTION_IS_EXIST);
		}
		CustomerAttention record = new CustomerAttention();
		record.setCreateTime(new Date());
		record.setUid(customer.getId());
		record.setPuid(id);
		// 执行添加
		return customerAttentionMapper.insert(record);
	}

	/**
	 * 用户取消关注
	 * 
	 * @param request
	 * @param id
	 *            被关注者id
	 * @return
	 */
	public int removeCustomerAttention(HttpServletRequest request, Integer id) {
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField, redisService);
		// 查看当前用户是否已关注被关注者
		CustomerAttention attention = customerAttentionMapper.selectByUidAndPuid(customer.getId(), id);
		if (attention == null) {
			throw new CommonException(StatusCode.ATTENTION_NOT_EXIST);
		}
		//执行删除操作
		return  customerAttentionMapper.deleteByPrimaryKey(attention.getId());
	}

	
	/**
	 * 得到我关注的人，分页获取
	 * @param request
	 * @return
	 */
	public PageInfo findMyAttention(HttpServletRequest request,Integer  pageIndex,Integer  pageSize) {
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField, redisService);
		PageHelper.startPage(pageIndex, pageSize);
		List<CustomerAttentionDto>  list = customerAttentionMapper.selectMyAttentionByIdMake(customer.getId());
		return new  PageInfo<>(list);
	}

	/**
	 * 得到关注我的人，分页获取
	 */
	public PageInfo findAttentionMy(HttpServletRequest request, Integer pageIndex, Integer pageSize) {
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField, redisService);
		PageHelper.startPage(pageIndex, pageSize);
		List<CustomerAttentionDto>  list = customerAttentionMapper.selectAttentionMyByIdMake(customer.getId());
		return new  PageInfo<>(list);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
