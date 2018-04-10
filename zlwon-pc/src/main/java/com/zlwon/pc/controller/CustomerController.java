package com.zlwon.pc.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.user.CustomerInfoDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.entity.Collection;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.config.UploadConfig;
import com.zlwon.server.service.CustomerService;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.vo.characteristic.CharacteristicDetailVo;
import com.zlwon.vo.customer.CustomerDetailVo;
import com.zlwon.vo.specification.SpecificationDetailVo;

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
	private UploadConfig uploadConfig;
	
	/**
	 * 根据用户id，得到用户信息，关注前查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryCustomer",method=RequestMethod.GET)
	public ResultData queryCustomer(HttpServletRequest  request,Integer  id){
		CustomerInfoDto customerInfoDto = customerService.findCustomerInfoByIdMake(request,id);
		return ResultData.one(customerInfoDto);
	}
	
	/**
	 * 根据物性ID查询物性表详情
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据token查询用户详情")
    @RequestMapping(value = "/queryCustomerInfoByToken", method = RequestMethod.GET)
	public ResultData queryCustomerInfoByToken(MultipartFile file,HttpServletRequest request){
		
		//验证token
		String token = request.getParameter("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		CustomerDetailVo result = customerService.findCustomerDetailById(user.getId());
		
		return ResultData.one(result);
	}
	
	/**
	 * 上传并修改用户头像
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "上传并修改用户头像")
    @RequestMapping(value = "/uploadSaveCustomerHeadImg", method = RequestMethod.POST)
	public ResultData uploadSaveCustomerHeadImg(MultipartFile file,HttpServletRequest request){
		
		//验证token
		String token = request.getParameter("token");
		
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
	 * 24小时换一次
	 * @return
	 */
	private  String changeFilesDri(){
		return System.currentTimeMillis()/100000000+"/";
	}
	
	
	
}
