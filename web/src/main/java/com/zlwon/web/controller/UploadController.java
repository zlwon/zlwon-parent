package com.zlwon.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zlwon.dto.web.upload.UploadBinaryFileDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rest.ResultData;
import com.zlwon.server.config.UploadConfig;
import com.zlwon.server.service.UploadService;
import com.zlwon.vo.file.FileUploadVo;

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
	private UploadConfig uploadConfig;
	
	@Autowired
	private UploadService uploadService;
	
	
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
    
    /**
     * web端上传单文件
     * @param file
     * @return
     */
    @ApiOperation(value = "web端上传单文件")
    @RequestMapping(value = "/uploadWebFile", method = RequestMethod.POST)
	public ResultData uploadWebFile(@RequestParam("file") MultipartFile file){
    	
    	FileUploadVo result = uploadService.uploadFile(file);
    	
		return ResultData.one(result);
	}
    
    /**
     * web端批量上传单文件
     * @param files
     * @return
     */
    @ApiOperation(value = "web端批量上传单文件")
    @RequestMapping(value = "/uploadBatchWebFile", method = RequestMethod.POST)
	public ResultData uploadBatchWebFile(@RequestParam("files") MultipartFile[] files){
    	
    	List<FileUploadVo> list = new ArrayList<FileUploadVo>();
    	
    	for(MultipartFile file : files) {
    		FileUploadVo result = uploadService.uploadFile(file);
    		list.add(result);  //将返回值填入
    	}
    	
		return ResultData.one(list);
	}
    
    /**
     * web端上传单图片，可压缩
     * @param file
     * @return
     */
    @ApiOperation(value = "web端上传单图片，可压缩")
    @RequestMapping(value = "/uploadWebThumbPicFile", method = RequestMethod.POST)
	public ResultData uploadWebThumbPicFile(@RequestParam("file") MultipartFile file){
    	
    	FileUploadVo result = uploadService.uploadThumbPicFile(file);
    	
		return ResultData.one(result);
	}
    
    /**
     * web端上传图片，并打上图片水印
     * @param file
     * @return
     */
    @ApiOperation(value = "web端上传图片，并打上图片水印")
    @RequestMapping(value = "/uploadWaterMarkPicFile", method = RequestMethod.POST)
	public ResultData uploadWaterMarkPicFile(@RequestParam("file") MultipartFile file){
    	
    	FileUploadVo result = uploadService.uploadWaterMarkFile(file);
    	
		return ResultData.one(result);
	}
    
    /**
     * web端批量上传图片，并打上图片水印
     * @param files
     * @return
     */
    @ApiOperation(value = "web端批量上传图片，并打上图片水印")
    @RequestMapping(value = "/uploadBatchWaterMarkPicFile", method = RequestMethod.POST)
	public ResultData uploadBatchWaterMarkPicFile(@RequestParam("files") MultipartFile[] files){
    	
    	List<FileUploadVo> list = new ArrayList<FileUploadVo>();
    	
    	for(MultipartFile file : files) {
    		FileUploadVo result = uploadService.uploadWaterMarkFile(file);
    		list.add(result);  //将返回值填入
    	}
    	
		return ResultData.one(list);
	}
    
    /**
     * 上传二进制图片
     * @param form
     * @return
     */
    @ApiOperation(value = "上传二进制图片")
    @RequestMapping(value = "/uploadBinaryFile", method = RequestMethod.POST)
    public ResultData uploadBinaryFile(UploadBinaryFileDto form){
    	
    	FileUploadVo result = uploadService.uploadBinaryFile(form.getPicByte());
    	
    	return ResultData.one(result);
    }
}
