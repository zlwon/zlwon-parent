package com.zlwon.api.controller;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.customer.UpdateCustomerDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.ExhibitionCase;
import com.zlwon.rdb.entity.InvitationRecord;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.*;
import com.zlwon.vo.customer.CustomerDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/customer")
public class CustomerApi extends BaseApi {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ExhibitionService exhibitionService;
	
	@Autowired
	private InvitationRecordService invitationRecordService;
	@Autowired
	private HelloService helloService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据展会案例ID查询工程师信息
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据展会案例ID查询工程师信息")
    @RequestMapping(value = "/queryEngineerInfoByExId/{exId}/{entryKey}", method = RequestMethod.GET)
    public ResultData queryEngineerInfoByExId(@PathVariable Integer exId,@PathVariable String entryKey){
        
		//验证参数
		if(exId == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		/*if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据展会案例ID查询展会案例信息
		ExhibitionCase mnb = exhibitionService.selectExhibitionCaseById(exId);
		if(mnb == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//根据用户ID查询用户
		Customer result = customerService.selectCustomerById(mnb.getUid());
		if(result == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		return ResultData.one(result);
    }

	/**
	 * 根据加密字符串查询用户信息
	 * @param entryKey
	 * @return
	 */
	@ApiOperation(value = "根据加密字符串查询用户信息")
    @RequestMapping(value = "/queryUserInfoByEntryKey", method = RequestMethod.GET)
	public ResultData queryUserInfoByEntryKey(@RequestParam String entryKey){
        
		//验证参数
		if(StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		/*if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据openID查询用户
		Customer result = customerService.selectCustomerByOpenId(openId);
		if(result == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		return ResultData.one(result);
    }
	
	/**
	 * 更新用户信息
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "更新用户信息")
    @RequestMapping(value = "/updateCustomerSupple", method = RequestMethod.POST)
	public ResultData updateCustomerSupple(UpdateCustomerDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String mail = form.getMail();  //邮箱
		String companyName = form.getCompanyName();  //公司名称
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(StringUtils.isBlank(mail) || StringUtils.isBlank(companyName) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		/*if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//验证邮箱
		Customer resultMail = customerService.selectCustomerByMail(mail);
		if(resultMail != null){
			return ResultData.error(StatusCode.MAIL_IS_REGISTER);
		}
		
		//根据openID查询用户
		Customer result = customerService.selectCustomerByOpenId(openId);
		if(result == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		result.setEmail(mail);
		result.setCompany(companyName);
		
		//拆取邮箱前半段
		String[] array = mail.split("@");
		String mailName = array[0];
		result.setNickname(mailName);
		
		//修改用户信息
		int count =  customerService.updateCustomer(result);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 判断用户身份，如果身份为1则为工程师，否则0为非工程师
	 * @param entryKey
	 * @return
	 */
	@ApiOperation(value = "判断用户身份")
    @RequestMapping(value = "/judgeUserRoleByEntryKey", method = RequestMethod.GET)
	public ResultData judgeUserRoleByEntryKey(@RequestParam String entryKey){
        
		//验证参数
		if(StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		/*if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据openID查询用户
		Customer result = customerService.selectCustomerByOpenId(openId);
		if(result == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		//查询该用户是否是工程师
		String flag = "0";
		List<InvitationRecord> list = invitationRecordService.selectInvitationRecordByUid(result.getId());
		if(list != null && list.size() > 0){
			flag = "1";
		}
		
		return ResultData.one(flag);
    }
	
	/**
	 * 根据加密字符串查询用户详细信息（包括身份）
	 * @param entryKey
	 * @return
	 */
	@ApiOperation(value = "根据加密字符串查询用户详细信息（包括身份）")
    @RequestMapping(value = "/queryUserDetailInfoByEntryKey", method = RequestMethod.GET)
	public ResultData queryUserDetailInfoByEntryKey(@RequestParam String entryKey){
        
		//验证参数
		if(StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		String openId = validLoginStatus(entryKey,redisService);
		/*if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据openID查询用户
		Customer result = customerService.selectCustomerByOpenId(openId);
		if(result == null){
			return ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		//查询该用户是否是工程师
		Integer flag = 0;
		List<InvitationRecord> list = invitationRecordService.selectInvitationRecordByUid(result.getId());
		if(list != null && list.size() > 0){
			flag = 1;
		}
		
		CustomerDetailVo customerInfo = new CustomerDetailVo();
		customerInfo.setId(result.getId());
		customerInfo.setRole(result.getRole());
		customerInfo.setNickname(result.getNickname());
		customerInfo.setName(result.getName());
		customerInfo.setMobile(result.getMobile());
		customerInfo.setCompany(result.getCompany());
		customerInfo.setOccupation(result.getOccupation());
		customerInfo.setBcard(result.getBcard());
		customerInfo.setHeaderimg(result.getHeaderimg());
		customerInfo.setIntegration(result.getIntegration());
		customerInfo.setGold(result.getGold());
		customerInfo.setEmail(result.getEmail());
		customerInfo.setIntro(result.getIntro());
		customerInfo.setMyinfo(result.getMyinfo());
		customerInfo.setLabel(result.getLabel());
		customerInfo.setApply(result.getApply());
		customerInfo.setOpenid(result.getOpenid());
		customerInfo.setRemark(result.getRemark());
		customerInfo.setIdentity(flag);
		
		return ResultData.one(customerInfo);
    }

	@ApiOperation(value = "根据加密字符串查询用户信息")
	@GetMapping(value = "/hello/{word}")
	public ResultData hello(@PathVariable String word){
		return ResultData.one(helloService.hello(word));
	}
}
