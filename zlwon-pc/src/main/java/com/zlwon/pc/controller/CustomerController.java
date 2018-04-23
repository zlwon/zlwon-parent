package com.zlwon.pc.controller;

import java.io.File;
import java.io.IOException;
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
import com.zlwon.dto.pc.customer.ModifyCustomerInfoDto;
import com.zlwon.exception.CommonException;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.CharacteristicBusiness;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.config.UploadConfig;
import com.zlwon.server.service.CharacteristicBusinessService;
import com.zlwon.server.service.CustomerService;
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
			if(StringUtils.isBlank(result.getLabel())){
				//查询标签信息
				List<CharacteristicBusiness> characterList = characteristicBusinessService.findCharacteristicBusinessByIdStr(result.getLabel());
				result.setCharacterList(characterList);;
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
		
		String realName = form.getRealName();  //真实姓名
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
		updateInfo.setName(realName);
		updateInfo.setHeaderimg(headerimg);
		updateInfo.setNickname(nickname);
		updateInfo.setCompany(companyName);
		updateInfo.setOccupation(occupation);
		updateInfo.setIntro(intro);
		updateInfo.setMyinfo(myinfo);
		updateInfo.setLabel(label);
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
}
