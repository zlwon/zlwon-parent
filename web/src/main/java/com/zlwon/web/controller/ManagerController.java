package com.zlwon.web.controller;


import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.Sysadmin;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ManagerService;
import com.zlwon.web.annotations.AuthLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * 管理员操作api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	
	
	/**
	 * 管理员登录
	 * @param username 账号
	 * @param password 密码
	 * @return
	 */
	@ApiOperation(value = "管理员登录")
	@RequestMapping(value="login",method=RequestMethod.POST)
	public ResultData login(String username, String password, HttpServletRequest request){
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return  ResultData.error(StatusCode.INVALID_PARAM);
		}
		Sysadmin sysadmin = managerService.login(username, password);
		if(sysadmin==null || sysadmin.getDel() != 1){
			//账号不存在
			return  ResultData.error(StatusCode.USER_NOT_EXIST);
		}
		
		String token = UUID.randomUUID().toString().replace("-", "");
		
		HttpSession session = request.getSession();
		//把token保存到session中,默认存活时间30分，不做修改
		session.setAttribute(token, session.getId());
		
		//封装到map中
		Map<String,Object>  map = new  HashMap<String,Object>();
		map.put("id", sysadmin.getId());
		map.put("username", sysadmin.getUsername());
		map.put("name", sysadmin.getName());
		map.put("createTime", sysadmin.getCreateTime());
		//返回token
		map.put("token", token);
		return  ResultData.one(map);
	}

	/**
	 * 添加管理员
	 * @param sysadmin
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="addManager",method=RequestMethod.POST)
	public   ResultData  addManager(Sysadmin  sysadmin){
		//执行添加操作
		managerService.saveManager(sysadmin);
		return  ResultData.ok();
	}
	
	
	/**
	 * 保存修改后的管理员信息，根据管理员id
	 * @param sysadmin
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="editManager",method=RequestMethod.POST)
	public  ResultData  editManager(Sysadmin  sysadmin){
		managerService.alterManager(sysadmin);
		return  ResultData.ok();
	}
	
	/**
	 * 得到所有管理员，分页查找
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="queryAllManager",method=RequestMethod.GET)
	public   ResultPage  queryAllManager(@RequestParam(defaultValue="1")Integer  pageIndex,
			@RequestParam(defaultValue="30")Integer pageSize){
		PageInfo<Sysadmin> info = managerService.findAllManager(pageIndex,pageSize);
		return  ResultPage.list(info);
	}
	
	/**
	 * 根据管理员id，删除管理员
	 * @param id
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="cancelManager",method=RequestMethod.GET)
	public  ResultData  cancelManager(Integer  id){
		managerService.removeManagerById(id);
		return  ResultData.ok();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
