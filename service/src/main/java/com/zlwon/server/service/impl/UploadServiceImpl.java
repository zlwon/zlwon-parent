package com.zlwon.server.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zlwon.exception.CommonException;
import com.zlwon.server.config.UploadConfig;
import com.zlwon.server.service.UploadService;
import com.zlwon.vo.file.FileUploadVo;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 上传ServiceImpl
 * @author yangy
 *
 */

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadConfig uploadConfig;
	
	/**
	 * 24小时换一次
	 * @return
	 */
	private static String changeFilesDri(){
		return System.currentTimeMillis()/100000000+"/";
	}
	
	/**
	 * 上传文件（包括图片）
	 * 不执行压缩
	 * @param file
	 * @return
	 */
	public FileUploadVo uploadFile(MultipartFile file){
		FileUploadVo returnInfo = new FileUploadVo();
		
		String changeFilesDri = changeFilesDri();
		String oldname = file.getOriginalFilename();  //源文件名称
		String ext = oldname.substring(oldname.lastIndexOf(".")+1);  //文件后缀名
		ext = ext.toLowerCase();
		returnInfo.setFileName(oldname);
		returnInfo.setFileType(ext);
		long timeMillis = System.currentTimeMillis();
		String storePath = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri;  //存储地址目录
		File saveFile = new File(storePath);
		if(!saveFile.exists()){  //创建目录
			saveFile.mkdirs();
		}
		
		String newName = oldname.substring(0,oldname.lastIndexOf(".")) + "-" + timeMillis;
		storePath = storePath + newName + "."+ext;  //存储地址
		returnInfo.setStoreUrl(storePath);  //存储地址
		
		try {
			file.transferTo(new File(storePath));  //存储当前文件
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		}
		
		String mappingPath = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" + changeFilesDri + newName + "."+ext;
		returnInfo.setMappingUrl(mappingPath);  //映射地址
		
		return returnInfo;
	}
	
	/**
	 * 上传图片，并执行压缩
	 * @param file
	 * @return
	 */
	public FileUploadVo uploadThumbPicFile(MultipartFile file){
		FileUploadVo returnInfo = new FileUploadVo();
		
		String changeFilesDri = changeFilesDri();
		String oldname = file.getOriginalFilename();  //源文件名称
		String ext = oldname.substring(oldname.lastIndexOf(".")+1);  //文件后缀名
		ext = ext.toLowerCase();
		returnInfo.setFileName(oldname);
		returnInfo.setFileType(ext);
		long timeMillis = System.currentTimeMillis();
		String storePath = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri;  //存储地址目录
		File saveFile = new File(storePath);
		if(!saveFile.exists()){  //创建目录
			saveFile.mkdirs();
		}
		
		String newName = oldname.substring(0,oldname.lastIndexOf(".")) + "-" + timeMillis;
		storePath = storePath + newName + "."+ext;  //存储地址
		returnInfo.setStoreUrl(storePath);  //存储地址
		
		try {
			file.transferTo(new File(storePath));  //存储当前文件
			String mappingPath = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" + changeFilesDri + newName + "."+ext;
			returnInfo.setMappingUrl(mappingPath);  //映射地址
			
			//压缩图片
			String typeImg = "jpg,png,jpeg,gif";
        	List fileTypes = getAllowFiles(typeImg);
        	if(fileTypes.contains(ext)){  //如果类型属于允许上传上传的文件类型
        		String storeSmallPath = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri;  //压缩图片存储地址
            	storeSmallPath = storeSmallPath+"compress"+newName+ "."+ext;
            	Thumbnails.of(storePath) 
    			.scale(1f) 
    			.outputQuality(0.5f) 
    			.toFile(storeSmallPath);
            	String smallurnUrl = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" +changeFilesDri+"compress"+newName+ "."+ext;
            	returnInfo.setThumbPicUrl(smallurnUrl);  //压缩图片地址
        	}
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  CommonException(e);
		}
		
		return returnInfo;
	}
	
	/**
	 * 获取可上传的文件类型
	 * @param allowFiles
	 * @return
	 */
	public static List getAllowFiles(String allowFiles){
		List fileTypes = new ArrayList();
		String types[] = allowFiles.split(",");
		for(int i = 0;i<types.length;i++){
			fileTypes.add(types[i]);
		}
		return fileTypes;
	}
}
