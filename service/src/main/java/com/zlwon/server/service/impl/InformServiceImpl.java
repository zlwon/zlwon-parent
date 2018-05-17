package com.zlwon.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.rdb.dao.InformMapper;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.server.service.InformService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.vo.pc.inform.InformLabelVo;
import com.zlwon.vo.pc.inform.InformListVo;

/**
 * 用户消息service
 * @author yuand
 *
 */
@Service
public class InformServiceImpl implements InformService {

	@Value("${pc.user.header}")
	private String token;
	@Value("${pc.redis.user.token.prefix}")
	private String tokenPrefix;
	@Value("${pc.redis.user.token.field}")
	private String tokenField;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private  InformMapper   informMapper;
	
	/**
	 * 得到用户所有消息，根据类型查找-分页查找
	 * @param type 0查所有1用户提问审核2用户回答审核3案例编辑审核4用户新增物性标签5材料报价单6用户认证
	 * @return
	 */
	@Override
	public PageInfo findAllInform(HttpServletRequest request,Integer pageIndex, Integer pageSize, Integer type) {
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField,redisService);
		PageHelper.startPage(pageIndex, pageSize);
		List<InformListVo>  list = informMapper.selectAllInformByUid(customer.getId(),type);
		return new PageInfo<>(list);
	}

	/**
	 * 得到用户未读消息总个数
	 * @param request
	 * @return
	 */
	@Override
	public int findBadgeNumber(HttpServletRequest request) {
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField,redisService);
		return informMapper.selectBadgeNumberByUid(customer.getId());
	}

	/**
	 * 设置消息已读
	 * @param ids 消息id数组
	 * @return
	 */
	@Override
	public int alterInformMakeReadByIds(HttpServletRequest request,String[] ids) {
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField,redisService);
		return informMapper.updateInformMakeReadByIds(customer.getId(),ids);
	}

	/**
	 * 得到用户消息个数(label)
	 * @param request
	 * @return
	 */
	@Override
	public InformLabelVo findLabelInformNumber(HttpServletRequest request) {
		InformLabelVo vo = new  InformLabelVo();
		// 查看当前用户信息
		Customer customer = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField,redisService);
		int  qNumber = informMapper.selectInformCountByUidAndType(customer.getId(),1,null);
		int  aNumber = informMapper.selectInformCountByUidAndType(customer.getId(),2,null);
		int  ceNumber = informMapper.selectInformCountByUidAndType(customer.getId(),3,null);
		int  chNumber = informMapper.selectInformCountByUidAndType(customer.getId(),4,null);
		int  dqNumber = informMapper.selectInformCountByUidAndType(customer.getId(),5,null);
		int  caNumber = informMapper.selectInformCountByUidAndType(customer.getId(),6,null);
		vo.setANumber(aNumber);
		vo.setCaNumber(caNumber);
		vo.setCeNumber(ceNumber);
		vo.setChNumber(chNumber);
		vo.setDqNumber(dqNumber);
		vo.setQNumber(qNumber);
		return vo;
	}

	
}
