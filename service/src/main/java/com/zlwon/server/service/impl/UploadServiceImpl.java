package com.zlwon.server.service.impl;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
	
	
	private static final String FONT_FAMILY = "微软雅黑";//字体
    private static final int FONT_STYLE = Font.BOLD;//字体加粗
    private static final int FONT_SIZE = 24;//字体大小
    private static final float ALPHA = 0.7F;//水印透明度

    private static final int LOGO_WIDTH = 100;//图片水印大小
	
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
	
	/**
	 * 上传图片，并打上图片水印
	 * @param file
	 * @return
	 */
	public FileUploadVo uploadWaterMarkFile(MultipartFile file){
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
		File storeFile = new File(storePath);
		
		try {
			file.transferTo(storeFile);  //存储当前文件
			
			//添加图片水印
			//读取原图片信息
			Image srcImage = ImageIO.read(storeFile);
			int width = srcImage.getWidth(null);
	        int height = srcImage.getHeight(null);
	        
	        //加水印
	        BufferedImage tarBuffImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = tarBuffImage.createGraphics();
	        g.drawImage(srcImage, 0, 0, width,height,null);
	        
	        //读取水印图片
	        String logoPath = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/systemImg/zhiliaoLogo.png";  //水印图片地址
	        Image logoImage = ImageIO.read(new File(logoPath));
	        int logoWidth = LOGO_WIDTH;
	        int logoHeight = (LOGO_WIDTH*logoImage.getHeight(null))/logoImage.getWidth(null);

	        int x=width-logoWidth;
	        int y=height-logoHeight;
	        
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
	        g.drawImage(logoImage, x, y, logoWidth, logoHeight, null);
	        g.dispose();
	        
	        String waterPath = uploadConfig.getDomainPath() + uploadConfig.getFilePath() + "/" + changeFilesDri+"water"+newName + "."+ext;  //水印图片存储地址
	        returnInfo.setStoreUrl(storePath);  //存储地址
	        ImageIO.write(tarBuffImage, "jpg", new File(waterPath));
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new CommonException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(e);
		}
		
		//原图映射
		String mappingPath = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" + changeFilesDri + newName + "."+ext;
		returnInfo.setMappingUrl(mappingPath);  //映射地址
		
		//水印图片映射
		String waterMappingPath = uploadConfig.getDomain() + uploadConfig.getFilePath() + "/" + changeFilesDri +"water"+ newName + "."+ext;
		returnInfo.setWaterPicUrl(waterMappingPath);  //水印图片映射地址
		
		return returnInfo;
	}
}
