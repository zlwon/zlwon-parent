package com.zlwon.pc.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.customer.ApplyCompanyCustomerDto;
import com.zlwon.dto.pc.customer.ModifyCustomerInfoDto;
import com.zlwon.exception.CommonException;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.CharacteristicBusiness;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.CustomerAuth;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.config.UploadConfig;
import com.zlwon.server.service.CharacteristicBusinessService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.UploadService;
import com.zlwon.vo.pc.customer.CustomerApplyInfoVo;
import com.zlwon.vo.pc.customer.CustomerInfoVo;
import com.zlwon.vo.pc.customer.PcCustomerDetailVo;
import com.zlwon.vo.pc.customer.ProducerVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/pc/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CharacteristicBusinessService characteristicBusinessService;
	
	@Autowired
	private UploadConfig uploadConfig;
	
	@Autowired
	private UploadService uploadService;
	
	/**
	 * 根据用户id，得到用户信息，关注前查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryCustomer",method=RequestMethod.GET)
	public ResultData queryCustomer(HttpServletRequest  request,Integer  id){
		CustomerInfoVo customerInfoDto = customerService.findCustomerInfoByIdMake(request,id);
		return ResultData.one(customerInfoDto);
	}
	
	/**
	 * 根据token查询用户详情
	 * @param id
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "根据token查询用户详情")
    @RequestMapping(value = "/queryCustomerInfoByToken", method = RequestMethod.GET)
	public ResultData queryCustomerInfoByToken(HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		PcCustomerDetailVo result = customerService.findCustomerDetailById(user.getId());
		
		if(result != null){
			if(StringUtils.isNotBlank(result.getLabel())){
				//查询标签信息
				List<CharacteristicBusiness> characterList = characteristicBusinessService.findCharacteristicBusinessByIdStr(result.getLabel());
				result.setCharacterList(characterList);
			}
		}
		
		return ResultData.one(result);
	}
	
	/**
	 * 上传并修改用户头像
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "上传并修改用户头像")
    @RequestMapping(value = "/uploadSaveCustomerHeadImg", method = RequestMethod.POST)
	public ResultData uploadSaveCustomerHeadImg(MultipartFile file,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//上传文件
		String oldname = file.getOriginalFilename();
		long timeMillis = System.currentTimeMillis();
		String changeFilesDri = changeFilesDri();
		String returnPath = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri;
		File oldFile = new File(returnPath);
		if(!oldFile.exists()){
			oldFile.mkdirs();
		}
		
		String newName = oldname.substring(0,oldname.lastIndexOf(".")) + "-" + timeMillis;
		newName = newName.substring(newName.lastIndexOf("\\")+1);//ie上传会带盘符，需要去掉
		returnPath = returnPath + newName + oldname.substring(oldname.lastIndexOf("."));

		try {
			file.transferTo(new File(returnPath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		}
		
		returnPath = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" + changeFilesDri + newName + oldname.substring(oldname.lastIndexOf("."));
		
		//保存头像
		Customer updateInfo = new Customer();
		updateInfo.setId(user.getId());
		updateInfo.setHeaderimg(returnPath);
		int count = customerService.updateCustomer(updateInfo);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * InputStream转byte[]
	 * @param input
	 * @return
	 * @throws IOException
	 */
	 public static byte[] toByteArray(InputStream input) throws IOException {
	        ByteArrayOutputStream output = new ByteArrayOutputStream();
	        byte[] buffer = new byte[4096];
	        int n = 0;
	        while (-1 != (n = input.read(buffer))) {
	            output.write(buffer, 0, n);
	        }
	        return output.toByteArray();
	    }
	
	/**
	 * 24小时换一次
	 * @return
	 */
	private  String changeFilesDri(){
		return System.currentTimeMillis()/100000000+"/";
	}
	
	/**
	 * 个人信息编辑
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "个人信息编辑")
    @RequestMapping(value = "/modifyCustomerInfo", method = RequestMethod.POST)
	public ResultData modifyCustomerInfo(ModifyCustomerInfoDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}

		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String email = form.getEmail();//邮箱
		String headerimg = form.getHeaderimg();  //用户头像
		String nickname = form.getNickname();  //用户昵称
		String companyName = form.getCompanyName();  //公司名称
		String occupation = form.getOccupation();  //职业经历
		String intro = form.getIntro();  //一句话简介
		String myinfo = form.getMyinfo();  //个人简介
		String label = form.getLabel();  //业务标签
		
		//更新数据
		Customer updateInfo = new Customer();
		updateInfo.setId(user.getId());
		updateInfo.setEmail(email);
		updateInfo.setHeaderimg(headerimg);
		updateInfo.setNickname(nickname);
		updateInfo.setCompany(companyName);
		updateInfo.setOccupation(occupation);
		updateInfo.setIntro(intro);
		updateInfo.setMyinfo(myinfo);
		updateInfo.setLabel(label);
		//判断邮箱是否重复
		Customer re = customerService.selectCustomerByMail(email);
		if(re != null  && !re.getId().equals(user.getId())){
			return   ResultData.error(StatusCode.MAIL_IS_REGISTER);
		}
		
		int count = customerService.updateCustomer(updateInfo);
		if(count == 0){
			return ResultData.error(StatusCode.SYS_ERROR);
		}
		
		return ResultData.ok();
	}
	
	
	/**
	 * 个人中心-应用案例,分页查找
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value = "queryMyApplicationCaseInfo", method = RequestMethod.GET)
	public   ResultPage   queryMyApplicationCaseInfo(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="10")Integer  pageSize,HttpServletRequest request){
		PageInfo  info = customerService.findMyApplicationCaseInfo(pageIndex,pageSize,request);
		return  ResultPage.list(info);
	}
	
	/**
	 * 得到所有生产商，不分页
	 * @return
	 */
	@RequestMapping(value = "queryProducer", method = RequestMethod.GET)
	public  ResultData  queryProducer(){
		List<ProducerVo> list = customerService.findProducer();
		return  ResultData.one(list);
	}
	
	
	
	
	/**
	 * 申请认证(普通用户和认证用户都可以申请，但是必须是无申请状态下的)
	 * @param request
	 * @param customerDto 提交的企业信息，目前只查看审核通过的企业(不考虑用户提交的企业和正在审核中的企业冲突)
	 * @param customerAuth 提交认证信息，会执行修改用户一些信息，需要保存，后台审核通过后，需要替换用户表中对应的信息
	 *		  customerAuth.type 认证类型1:个人认证6:企业认证
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value = "apply2CompanyCustomer", method = RequestMethod.POST)
	public  ResultData  apply2CompanyCustomer(HttpServletRequest request,ApplyCompanyCustomerDto customerDto,CustomerAuth  customerAuth){
		customerService.alter2CompanyCustomer(request,customerDto,customerAuth);
		return  ResultData.ok();
	}
	
	
	/**
	 * 得到用户认证信息-根据认证状态
	 * @param request
	 * @param type 认证状态1个人认证6企业认证
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value = "queryApplyInfo", method = RequestMethod.GET)
	public  ResultData  queryApplyInfo(HttpServletRequest request,Integer  type){
		CustomerApplyInfoVo infoVo = customerService.findApplyInfo(request,type);
		return  ResultData.one(infoVo);
	}
	
	
	/**
	 * 根据用户昵称模糊查询用户信息
	 * @param userName
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据用户昵称模糊查询用户信息")
    @RequestMapping(value = "/queryCustomerByLikeName", method = RequestMethod.GET)
	public ResultData queryCustomerByLikeName(@RequestParam String userName,HttpServletRequest request){
		
		//验证参数
		if(StringUtils.isBlank(userName)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据用户昵称模糊查询用户信息
		List<Customer> result = customerService.findCustomerByLikeName(userName);
		
		return ResultData.one(result);
	}
	
	/**
	 * 根据用户昵称模糊查询用户信息-认证用户及企业用户
	 * @param userName
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据用户昵称模糊查询用户信息-认证用户及企业用户")
    @RequestMapping(value = "/queryCustomerByLikeNameLimit", method = RequestMethod.GET)
	public ResultData queryCustomerByLikeNameLimit(@RequestParam String userName,HttpServletRequest request){
		
		//验证参数
		if(StringUtils.isBlank(userName)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据用户昵称模糊查询用户信息-认证用户及企业用户
		List<Customer> result = customerService.findCustomerByLikeNameLimit(userName);
		
		return ResultData.one(result);
	}
	
	/**
	 * 根据用户ID字符串查询用户信息
	 * @param idStr
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据用户ID字符串查询用户信息")
    @RequestMapping(value = "/queryCustomerByidStr", method = RequestMethod.GET)
	public ResultData queryCustomerByidStr(@RequestParam String idStr,HttpServletRequest request){
		
		//验证参数
		if(StringUtils.isBlank(idStr)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据用户ID字符串查询用户信息
		List<Customer> result = customerService.findCustomerByidStr(idStr);
		
		return ResultData.one(result);
	}
	
	
	/**
	 * 申请认证知料师，只能个人认证用户申请
	 * @param request
	 * @param type 1知料师2高级知料师(积分达到500)3首席知料师(积分达到1000)
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value = "apply2ExpertCustomer", method = RequestMethod.POST)
	public  ResultData  apply2ExpertCustomer(HttpServletRequest request,@RequestParam(defaultValue="1")Integer  type){
		customerService.alter2ExpertCustomer(request,type);
		return  ResultData.ok();
	}
	
	/**
	 * 申请成为认证用户(必须上传自己名片),作废了，认证用户也要关联企业，
	 * @param bcard 名片路径
	 * @return
	 */
	/*@AuthLogin
	@RequestMapping(value = "apply2AuthenticateCustomer", method = RequestMethod.POST)
	public   ResultData  apply2AuthenticateCustomer(HttpServletRequest request,String  bcard){
		customerService.alter2AuthenticateCustomer(request,bcard);
		return  ResultData.ok();
	}*/
	
	
	
	
	
}
