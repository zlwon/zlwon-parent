package com.zlwon.api.controller;

import com.zlwon.api.config.UploadConfig;
import com.zlwon.exception.CommonException;
import com.zlwon.rest.ResultData;
import com.zlwon.vo.file.FileUploadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传文件api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/upload")
public class UploadApi extends BaseApi {

	@Autowired
	@Qualifier("uploadConfigApi")
	private  UploadConfig   uploadConfig;
	
	/**
	 * 24小时换一次
	 * @return
	 */
	private static String changeFilesDri(){
		return System.currentTimeMillis()/100000000+"/";
	}
	/**
	 * 上传mp3文件
	 * @param file
	 * @return
	 */
    @ApiOperation(value = "上传mp3文件")
    @RequestMapping(value = "/uploadMp3File", method = RequestMethod.POST)
	public ResultData uploadMp3File(MultipartFile file){
    	
    	FileUploadVo fileInfo = new FileUploadVo();
    	
    	String uuid = UUID.randomUUID().toString().replace("-", "");
    	String  changeFilesDri = changeFilesDri();
		String oldname = file.getOriginalFilename();
		String na = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri;
		File   oldFile = new File(na);
		if(!oldFile.exists()){
			oldFile.mkdirs();
		}
		
		String  newName = uuid;
		na = na + newName + oldname.substring(oldname.lastIndexOf("."));
		fileInfo.setStoreUrl(na);

		try {
			file.transferTo(new  File(na));
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		}
		
		na = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" + changeFilesDri + newName + oldname.substring(oldname.lastIndexOf("."));
		fileInfo.setMappingUrl(na);
		
		return  ResultData.one(fileInfo);
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ResultData uploadFile(MultipartFile file){
    	
    	FileUploadVo fileInfo = new FileUploadVo();
    	
    	String  changeFilesDri = changeFilesDri();
		String oldname = file.getOriginalFilename();
		long timeMillis = System.currentTimeMillis();
		String na = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri;
		File   oldFile = new File(na);
		if(!oldFile.exists()){
			oldFile.mkdirs();
		}
		
		String  newName = oldname.substring(0,oldname.lastIndexOf(".")) + "-" + timeMillis;
		na = na + newName + oldname.substring(oldname.lastIndexOf("."));
		fileInfo.setStoreUrl(na);
		
		try {
			file.transferTo(new  File(na));
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		}
		
		na = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" + changeFilesDri + newName + oldname.substring(oldname.lastIndexOf("."));
		fileInfo.setMappingUrl(na);
		
		return  ResultData.one(fileInfo);
	}
}
