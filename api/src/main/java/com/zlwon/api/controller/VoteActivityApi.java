package com.zlwon.api.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.voteActivity.*;
import com.zlwon.rdb.entity.*;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.*;
import com.zlwon.vo.voteActivity.VoteProjectDetailListVo;
import com.zlwon.vo.voteActivity.VoteProjectDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 投票活动api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/voteActivity")
public class VoteActivityApi extends BaseApi {

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private VoteActivityService voteActivityService;
	
	@Autowired
	private VoteProjectService voteProjectService;
	
	@Autowired
	private VoteProjectRecordService voteProjectRecordService;
	
	@Autowired
	private VoteProjectMessageService voteProjectMessageService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 根据活动ID查询投票活动信息
	 * @param id
	 * @param entryKey
	 * @return
	 */
	@ApiOperation(value = "根据活动ID查询投票活动信息")
    @RequestMapping(value = "/queryVoteActivityById", method = RequestMethod.GET)
    public ResultData queryVoteActivityById(@RequestParam Integer id,@RequestParam String entryKey){
	
		//验证参数
		if(id == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据活动ID查询投票活动信息
		VoteActivity temp = voteActivityService.selectVoteActivityById(id);
		
		return ResultData.one(temp);
	}
	
	/**
	 * 根据项目ID查询投票项目信息
	 * @param id
	 * @param entryKey
	 * @return
	 */
	@ApiOperation(value = "根据项目ID查询投票项目信息")
    @RequestMapping(value = "/queryVoteProjectById", method = RequestMethod.GET)
    public ResultData queryVoteProjectById(@RequestParam Integer id,@RequestParam String entryKey){
		
		//验证参数
		if(id == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据项目ID查询投票项目信息
		//VoteProject temp = voteProjectService.selectVoteProjectById(id);
		VoteProjectDetailVo temp = voteProjectService.selectVoteProjectDetailById(id);
		
		return ResultData.one(temp);
	}
	
	/**
	 * 分页显示所有在期限内的活动
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "分页显示所有在期限内的活动")
    @RequestMapping(value = "/queryVoteActivityByPage", method = RequestMethod.POST)
    public ResultPage queryVoteActivityByPage(VoteActivityPageDto form){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		//验证参数
		if(StringUtils.isBlank(entryKey) || currentPage == null || pageSize == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//分页显示所有在期限内的活动
		PageInfo<VoteActivity> pageList = voteActivityService.selectAllVoteActivityDate(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * 根据活动ID分页查询该活动所有参与项目
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "根据活动ID分页查询该活动所有参与项目")
    @RequestMapping(value = "/queryVoteProjectByPage", method = RequestMethod.POST)
    public ResultPage queryVoteProjectByPage(VoteProjectPageDto form){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer activityId = form.getActivityId();  //活动ID
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		//验证参数
		if(StringUtils.isBlank(entryKey) || activityId == null || currentPage == null || pageSize == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//根据活动ID分页查询该活动所有参与项目
		PageInfo<VoteProjectDetailListVo> pageList = voteProjectService.selectVoteProjectByActivityId(form);
		
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * 新增投票项目
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "新增投票项目")
    @RequestMapping(value = "/addVoteProject", method = RequestMethod.POST)
    public ResultData addVoteProject(AddVoteProjectDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer aid = form.getAid();  //活动表ID
		String photo = form.getPhoto();  //图片
		String title = form.getTitle();  //信息标题
		String entryKey = form.getEntryKey();  //微信加密字符串
		Integer fileType = form.getFileType();  //文件类型  1：图片  2：视频
		
		//验证参数
		if(aid == null || StringUtils.isBlank(entryKey) ||
				StringUtils.isBlank(photo) || StringUtils.isBlank(title) || fileType == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		try{
			//验证用户
			String openId = validLoginStatus(entryKey,redisService);
			if(StringUtils.isBlank(openId)){
				return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
			}
			
			//根据openId获取用户信息
			Customer user = customerService.selectCustomerByOpenId(openId);
			if(user == null){
				return ResultData.error(StatusCode.USER_NOT_EXIST);
			}
			
			VoteProject addInfo = new VoteProject();
			addInfo.setAid(aid);
			addInfo.setPhoto(photo);
			addInfo.setFileType(fileType);
			addInfo.setTitle(title);
			addInfo.setUid(user.getId());
			addInfo.setSupportNums(0);
			addInfo.setCreateTime(new Date());
			addInfo.setExamine(1);
			
			//新增投票项目
			int count = voteProjectService.insertVoteProject(addInfo);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 用户投票-点赞
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "用户投票-点赞")
    @RequestMapping(value = "/addVoteProjectRecord", method = RequestMethod.POST)
    public ResultData addVoteProjectRecord(AddVoteProjectRecordDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer activityId = form.getActivityId();  //活动ID
		Integer projectId = form.getProjectId();  //项目ID
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		//验证参数
		if(activityId == null || projectId == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			//验证用户
			//String openId = "olEcu5UJnaCIvSiyd3PENVshgLsY";
			String openId = validLoginStatus(entryKey,redisService);
			if(StringUtils.isBlank(openId)){
				return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
			}
			
			//根据活动ID查询投票活动信息
			VoteActivity actInfo = voteActivityService.selectVoteActivityById(activityId);
			if(actInfo == null){
				return ResultData.error(StatusCode.DATA_NOT_EXIST);
			}
			
			//转化时间
			String handleData = smf.format(actInfo.getAttendEndDate())+" 23:59:59";
			Date endDate = sdf.parse(handleData);
			
			//获取当前时间
			Date nowDate= new Date();
			
			long nowmins = nowDate.getTime();
			long endmins = endDate.getTime();
			
			//比较日期大小
			if(nowmins > endmins){
				return ResultData.error(StatusCode.VOTE_RECORD_OVER);
			}
			
			//根据openId获取用户信息
			Customer user = customerService.selectCustomerByOpenId(openId);
			if(user == null){
				return ResultData.error(StatusCode.USER_NOT_EXIST);
			}
			
			//验证投票项目是否存在
			VoteProject myProject = voteProjectService.selectVoteProjectById(projectId);
			if(myProject == null){
				return ResultData.error(StatusCode.DATA_NOT_EXIST);
			}
			
			//根据用户ID和项目ID，日期查询用户是否已经参与今日投票
			VoteProjectRecord validInfo = voteProjectRecordService.selectRecordByUidProDate(user.getId(), projectId);
			if(validInfo != null){
				return ResultData.error(StatusCode.VOTE_IS_EXIST);
			}
			
			VoteProjectRecord addInfo = new VoteProjectRecord();
			addInfo.setAid(activityId);
			addInfo.setIid(projectId);
			addInfo.setUid(user.getId());
			addInfo.setCreateTime(new Date());
			
			//新增投票项目
			int count = voteProjectRecordService.insertVoteProjectRecord(addInfo);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
			//投票成功后，修改投票项目的票数，加1
			VoteProject upTemp = new VoteProject();
			upTemp.setSupportNums(myProject.getSupportNums()+1);
			upTemp.setId(projectId);
			
			int newCount = voteProjectService.updateByPrimaryKeySelective(upTemp);
			if(newCount == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 用户对投票项目点评
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "用户对投票项目点评")
    @RequestMapping(value = "/addVoteProjectMessage", method = RequestMethod.POST)
    public ResultData addVoteProjectMessage(AddVoteProjectMessageDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer activityId = form.getActivityId();  //活动ID
		Integer projectId = form.getProjectId();  //项目ID
		String messageInfo = form.getMessageInfo();  //点评内容
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		//验证参数
		if(activityId == null || projectId == null || StringUtils.isBlank(messageInfo) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			//验证用户
			//String openId = "olEcu5UJnaCIvSiyd3PENVshgLsY";
			String openId = validLoginStatus(entryKey,redisService);
			if(StringUtils.isBlank(openId)){
				return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
			}
			
			//根据活动ID查询投票活动信息
			VoteActivity actInfo = voteActivityService.selectVoteActivityById(activityId);
			if(actInfo == null){
				return ResultData.error(StatusCode.DATA_NOT_EXIST);
			}
			
			//转化时间
			String handleData = smf.format(actInfo.getAttendEndDate())+" 23:59:59";
			Date endDate = sdf.parse(handleData);
			
			//获取当前时间
			Date nowDate= new Date();
			
			long nowmins = nowDate.getTime();
			long endmins = endDate.getTime();
			
			//比较日期大小
			if(nowmins > endmins){
				return ResultData.error(StatusCode.VOTE_MESSAGE_OVER);
			}
			
			//根据openId获取用户信息
			Customer user = customerService.selectCustomerByOpenId(openId);
			if(user == null){
				return ResultData.error(StatusCode.USER_NOT_EXIST);
			}
			
			//验证投票项目是否存在
			VoteProject myProject = voteProjectService.selectVoteProjectById(projectId);
			if(myProject == null){
				return ResultData.error(StatusCode.DATA_NOT_EXIST);
			}
			
			VoteProjectMessage addInfo = new VoteProjectMessage();
			addInfo.setAid(activityId);
			addInfo.setIid(projectId);
			addInfo.setUid(user.getId());
			addInfo.setMessageInfo(messageInfo);
			addInfo.setCreateTime(new Date());
			
			//新增投票项目评论
			int count = voteProjectMessageService.insertVoteProjectMessage(addInfo);
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 根据活动ID查询参与人数（根据特定算法规则计算）
	 * @param activityId
	 * @return
	 */
	@ApiOperation(value = "根据活动ID查询参与人数（根据特定算法规则计算）")
    @RequestMapping(value = "/queryVoteJoinCount", method = RequestMethod.GET)
    public ResultData queryVoteJoinCount(@RequestParam Integer activityId){
		
		//验证参数
		if(activityId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//查询总点赞数
		int recordCount = voteProjectRecordService.countProjectRecordByActivityId(activityId);
		
		//查询总评论数
		int messageCount = voteProjectMessageService.countProjectMessageByActivityId(activityId);
		
		//根据点赞数和评论数各取一半的公式得出最终结果人数
		int result = (recordCount+messageCount)/2;
		
		return ResultData.one(result);
	}
}
