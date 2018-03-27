package com.zlwon.web.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zlwon.exception.CommonException;
import com.zlwon.rest.ResultData;
import com.zlwon.web.config.UploadConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 上传文件api
 * @author yuand
 *
 */

@Api
@RestController
@RequestMapping("/upload")
public class UploadController{

	@Autowired
	private  UploadConfig   uploadConfig;
	
	/**
	 * 24小时换一次
	 * @return
	 */
	private  String changeFilesDri(){
		return System.currentTimeMillis()/100000000+"/";
	}
    
    /**
	 * 文件上传(支持单，多文件上传)
	 * @param file
	 * @return
	 */
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ResultData uploadFile(@RequestParam("file") MultipartFile[] files){
    	String  changeFilesDri = changeFilesDri();
    	StringBuilder  sb = new  StringBuilder();
    	
    	for (MultipartFile file : files) {
    		sb.append(uoloadFileFun(file,changeFilesDri)+",");
		}
		return  ResultData.one(sb.toString().substring(0, sb.toString().length()-1));
	}
    
    
    /**
     * 保存图片，返回图片路径
     * @param file
     * @param changeFilesDri
     * @return
     */
    private  String   uoloadFileFun(MultipartFile file,String  changeFilesDri){
		String oldname = file.getOriginalFilename();
		long timeMillis = System.currentTimeMillis();
		String na = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri;
		File   oldFile = new File(na);
		if(!oldFile.exists()){
			oldFile.mkdirs();
		}
		
		String  newName = oldname.substring(0,oldname.lastIndexOf(".")) + "-" + timeMillis;
		na = na + newName + oldname.substring(oldname.lastIndexOf("."));

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
		
		return  na;
    }
}
