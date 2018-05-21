package com.zlwon.web.config.ueditorConfig.upload;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zlwon.web.config.ueditorConfig.PathFormat;
import com.zlwon.web.config.ueditorConfig.define.AppInfo;
import com.zlwon.web.config.ueditorConfig.define.BaseState;
import com.zlwon.web.config.ueditorConfig.define.FileType;
import com.zlwon.web.config.ueditorConfig.define.State;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BinaryUploader {
	
	private static final String FONT_FAMILY = "微软雅黑";//字体
    private static final int FONT_STYLE = Font.BOLD;//字体加粗
    private static final int FONT_SIZE = 24;//字体大小
    private static final float ALPHA = 0.7F;//水印透明度

    private static final int LOGO_WIDTH = 100;//图片水印大小

    public static final State save(HttpServletRequest request, Map<String, Object> conf) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("upfile");
        try {
            if (file == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }

            String savePath = (String) conf.get("savePath");  //形如/ueditor/image/{yyyy}{mm}{dd}/{time}{rand:6}
            String originFileName = file.getOriginalFilename();  //获取文件名称如xxx.jpg
            String suffix = FileType.getSuffixByFilename(file.getOriginalFilename());   //获取后缀名

            originFileName = originFileName.substring(0,
                    originFileName.length() - suffix.length());  //获取原文件名称，仅名称
            String waterPath = savePath+ "water" + suffix;  //水印图片地址
            savePath = savePath + suffix;  //形如/ueditor/image/{yyyy}{mm}{dd}/{time}{rand:6}.jpg

            long maxSize = ((Long) conf.get("maxSize")).longValue();

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {  //验证是否属于可上传文件类型
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);  // /ueditor/image/20180521/1526872456023087053.jpg
            waterPath = PathFormat.parse(waterPath, originFileName);
            String physicalPath = (String) conf.get("rootPath") + savePath;  //H:/upload//ueditor/image/20180521/1526872456023087053.jpg
            String waterStorePath = (String) conf.get("rootPath") + waterPath;

            InputStream is = file.getInputStream();
            State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
            is.close();
            
            //水印图片相关
            //读取原图片信息
            File nowfile = new File(physicalPath);
			Image srcImage = ImageIO.read(nowfile);
			int width = srcImage.getWidth(null);
	        int height = srcImage.getHeight(null);
	        
	        //加水印
	        BufferedImage tarBuffImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = tarBuffImage.createGraphics();
	        g.drawImage(srcImage, 0, 0, width,height,null);
	        
	        //读取水印图片
	        String logoPath = (String) conf.get("rootPath")+"/upload/systemImg/zhiliaoLogo.png";  //水印图片地址
	        Image logoImage = ImageIO.read(new File(logoPath));
	        int logoWidth = LOGO_WIDTH;
	        int logoHeight = (LOGO_WIDTH*logoImage.getHeight(null))/logoImage.getWidth(null);

	        int x=width-logoWidth;
	        int y=height-logoHeight;
	        
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
	        g.drawImage(logoImage, x, y, logoWidth, logoHeight, null);
	        g.dispose();
	        
	        ImageIO.write(tarBuffImage, "jpg", new File(waterStorePath));

            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(waterPath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (IllegalStateException e) {
        	e.printStackTrace();
        } 
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
