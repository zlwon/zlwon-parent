package com.zlwon.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zlwon.server.config.UploadConfig;
import com.zlwon.utils.HttpUtils;

/**
 * 得到小程序指定页面二维码
 * 
 * @author FelixChen
 *
 */
public class QRCodeUtil {

	
	/**
	 * 获取二维码   接口B：适用于需要的码数量极多的业务场景，知料没用该接口
	 * @param request
	 * @param token access_token值
	 * @param page  必须是已经发布的小程序存在的页面（否则报错），例如 "pages/index/index"
	 * @param scene 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，
	 * @param imageName 二维码名称，介意使用案例主键id
	 * @param uploadConfig
	 * 				domainPath 保存到本地的绝对根路径，例如/usr/local/www
	 * 		 		filePathQR 保存根路径下的文件夹,例如/QRCoed，最后图片路径在/usr/local/www/QRCoed/imageName.png
	 * 				domain     域名
	 * @return 返回二维码网络路径
	 * @throws Exception
	 */
    public static String getWxacodeunlimit(HttpServletRequest request,String  token,String  page,String  scene,String  imageName,UploadConfig   uploadConfig) throws Exception {
        
        
        Map<String, Object> params = new HashMap<>();
        params.put("scene", scene);  //参数
        params.put("page", page); //位置
        params.put("width", 430);

        CloseableHttpClient  httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token);  // 接口
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = JSON.toJSONString(params);           //必须是json模式的 post      
        StringEntity entity;
        entity = new StringEntity(body);
        entity.setContentType("image/png");

        httpPost.setEntity(entity);
        HttpResponse response;

        response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        String name = imageName+".png";
        saveToImgByInputStream(inputStream,uploadConfig.getDomainPath()+uploadConfig.getFilePathQR(),name);  //保存图片
        
        return uploadConfig.getDomain()+uploadConfig.getFilePathQR() +"/"+ name;
    }
    
    
    /**
     * 获取二维码   接口A: 适用于需要的码数量较少的业务场景，知料使用该接口
     * @param request
     * @param token access_token值
     * @param path 跳转页面，参数get拼接
     * @param imageName 二维码名称，介意使用案例主键id
     * @param uploadConfig
     * 				domainPath 保存到本地的绝对根路径，例如/usr/local/www
     * 		 		filePathQR 保存根路径下的文件夹,例如/QRCoed，最后图片路径在/usr/local/www/QRCoed/imageName.png
     * 				domain     域名
     * @return 返回二维码网络路径
     * @throws Exception
     */
    public static String getWxacode(HttpServletRequest request,String  token,String  path,String  imageName,UploadConfig   uploadConfig){
    	
    	
    	Map<String, Object> params = new HashMap<>();
    	params.put("path", path);  //参数
    	params.put("width", 430);
    	
    	CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
    	
    	HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacode?access_token="+token);  // 接口
    	httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
    	String body = JSON.toJSONString(params);           //必须是json模式的 post      
    	StringEntity entity;
    	try {
			entity = new StringEntity(body);
			entity.setContentType("image/png");
	    	
	    	httpPost.setEntity(entity);
	    	HttpResponse response;
	    	
	    	response = httpClient.execute(httpPost);
	    	InputStream inputStream = response.getEntity().getContent();
	    	String name = imageName+".png";
	    	saveToImgByInputStream(inputStream,uploadConfig.getDomainPath()+uploadConfig.getFilePathQR(),name);  //保存图片
	    	
	    	return uploadConfig.getDomain()+uploadConfig.getFilePathQR() +"/"+ name;
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
    	
    	
    }
	
	
	/**
     * 将二进制转换成文件保存
     * @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return 
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgByInputStream(InputStream instreams,String imgPath,String imgName){
        int stateInt = 1;
        if(instreams != null){
            try {
                File file=new File(imgPath);//可以是任何图片格式.jpg,.png等
                if(!file.exists()){
                	file.mkdirs();
        		}
                file = new  File(imgPath,imgName);
                
                FileOutputStream fos=new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();                
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
            }
        }
        return stateInt;
    }
	
	
	/**
	 * 获取 token 普通的 get 可获 token
	 */
	public static String getToken(String  appid,String  secret) {
		try {

			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("grant_type", "client_credential");
			map.put("appid", appid);
			map.put("secret", secret);

			
			StringBuilder  param = new  StringBuilder();
			param.append("grant_type=client_credential&");
			param.append("appid="+appid+"&");
			param.append("secret="+secret);
			JSONObject json = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", param.toString());


			if (json.getString("access_token") != null || json.getString("access_token") != "") {
				return json.getString("access_token");
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
