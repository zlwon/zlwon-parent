package com.zlwon.api.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.scanCard.BaiduCloudTokenDto;
import com.zlwon.dto.scanCard.ScanBaiduCloudOcrDto;
import com.zlwon.dto.scanCard.ScanCamCardOcrDto;
import com.zlwon.dto.scanCard.ScanTencentOcrDto;
import com.zlwon.rest.ResultData;
import com.zlwon.utils.TencentUtils;
import com.zlwon.vo.scanCard.ScanCardSketchyVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 扫描（名片相关）api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/scanCard")
public class ScanCardApi {

	/**
	 * 获取百度云token
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "获取百度云token")
    @RequestMapping(value = "/obtainBaiduCloudToken", method = RequestMethod.POST)
    public ResultData obtainBaiduCloudToken(BaiduCloudTokenDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String clientId = form.getClientId();  //必须参数，应用的API Key
		String clientSecret = form.getClientSecret();  //必须参数，应用的Secret Key
		
		//验证参数
		if(StringUtils.isBlank(clientId) || StringUtils.isBlank(clientSecret)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//获取token
		String token = getAuth(clientId,clientSecret);
		
		return ResultData.one(token);
	}
	
	/**
	 * 调用百度云OCR
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "调用百度云OCR")
    @RequestMapping(value = "/scanBaiduCloudOcr", method = RequestMethod.POST)
    public ResultData scanBaiduCloudOcr(ScanBaiduCloudOcrDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String url = form.getUrl();  //图片网络路径
		String languageType = form.getLanguageType();  //识别语言类型,默认为CHN_ENG
		String detectDirection = form.getDetectDirection();  //是否检测图像朝向，默认false
		String detectLanguage = form.getDetectLanguage();  //是否检测语言，默认false
		String probability = form.getProbability();   //是否返回识别结果中每一行的置信度,默认false
		String accessToken = form.getAccessToken();  //token
		
		//验证参数
		if(StringUtils.isBlank(url) || StringUtils.isBlank(languageType) || StringUtils.isBlank(detectDirection)
				|| StringUtils.isBlank(detectLanguage) || StringUtils.isBlank(probability) || StringUtils.isBlank(accessToken)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		BufferedReader reader = null;    
        String result = null;    
        StringBuffer sbf = new StringBuffer();
        ScanCardSketchyVo temp = new ScanCardSketchyVo();
        String tempstr = "";
        
        try{
        	//处理图片
        	//new一个URL对象  
            URL picUrl = new URL(url);  
            //打开链接  
            HttpURLConnection conn = (HttpURLConnection)picUrl.openConnection();  
            //设置请求方式为"GET"  
            conn.setRequestMethod("GET");  
            //超时响应时间为5秒  
            conn.setConnectTimeout(5 * 1000);  
            //通过输入流获取图片数据  
            InputStream inStream = conn.getInputStream();  
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
            byte[] data = readInputStream(inStream);
        	
            //对字节数组Base64编码    
            Base64.Encoder encoder = Base64.getEncoder();
            String encodedText = encoder.encodeToString(data);
        	
        	//post参数
        	Map<String,Object> params = new LinkedHashMap<>();
        	params.put("language_type", languageType);
        	params.put("detect_direction", detectDirection);
        	params.put("detect_language", detectLanguage);
        	params.put("probability", probability);
        	params.put("image", encodedText);
        	 
        	//转化参数
        	StringBuilder postData = new StringBuilder();
        	for (Map.Entry<String,Object> param : params.entrySet()) {
        		if (postData.length() != 0) postData.append('&');
        	 	postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
        	 	postData.append('=');
        	 	postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        	}
        	byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        	
        	//得到URL对象
        	URL psotUrl = new URL("https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token="+accessToken);    
            //打开连接
        	HttpURLConnection connection = (HttpURLConnection) psotUrl    
                    .openConnection();
        	//设置提交类型 
            connection.setRequestMethod("POST"); 
            //设置标题头
            connection.setRequestProperty("Content-Type",    
                    "application/x-www-form-urlencoded");
            //设置允许写出数据,默认是不允许 false 
            connection.setDoOutput(true); 
            connection.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true    
            connection.getOutputStream().write(postDataBytes);    
            connection.connect();    
            InputStream is = connection.getInputStream();    
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));    
            String strRead = null;    
            while ((strRead = reader.readLine()) != null) {    
                sbf.append(strRead);  
            }    
            reader.close();    
            result = sbf.toString(); 
            
            //处理字符串结果
            temp = handleScanCardResult(result);
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
		
		return ResultData.one(temp);
	}
	
    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
    
    /**
     * 处理扫描字符串
     * @param sacnResult  扫描字符串结果
     * @return
     */
    public static ScanCardSketchyVo handleScanCardResult(String sacnResult){

    	ScanCardSketchyVo temp = new ScanCardSketchyVo();
    	
    	JSONObject jsonObject = new JSONObject(sacnResult);
    	Integer wordsResultNum = jsonObject.getInt("words_result_num");  //获取处理字符串数量
    	JSONArray jsonArray = jsonObject.getJSONArray("words_result");  //获取处理字符串数组
    	
    	//将判断出的字符放进返回对象
    	for(int i=0;i<wordsResultNum;i++){
    		JSONObject tempObject = jsonArray.getJSONObject(i);
    		String tempString = tempObject.getString("words");  //获取要处理的字符串
    		
    		//处理邮件
    		String mailResult = judgeMail(tempString);
    		if(StringUtils.isNotBlank(mailResult)){
    			temp.setMail(mailResult);
    			continue;
    		}
    		
    		
    		//处理手机号
    		String mobileResult = judgeMobile(tempString);
    		if(StringUtils.isNotBlank(mobileResult)){
    			temp.setMobile(mobileResult);
    			continue;
    		}
    	}
    	
    	temp.setRemark(sacnResult);
    	
    	return temp;
    }
    
    /**
     * 判断要处理字符串是否包含邮箱，并返回邮箱
     * @param handleStr
     * @return
     */
    public static String judgeMail(String handleStr){
    	
    	//去除邮箱两侧及中间空格
    	handleStr = handleStr.replace(" ", "");
    	//去除邮箱中的(
    	handleStr = handleStr.replace("(", "");
    	//去除所有邮箱中文：
    	handleStr = handleStr.replace("：", "");
    	//去除所有邮箱英文:
    	handleStr = handleStr.replace(":", "");
    	
    	String mailStr = "";
    	
    	String[] mailTag = new String[]{"Email","邮件","E-mail","邮箱","E-MAIL","邮箱Email","Mail","电邮","e-mail",
    			"email"};
    	for(int i = 0;i<mailTag.length;i++){
    		if(handleStr.indexOf(mailTag[i])!=-1){
    			mailStr = handleStr.substring(handleStr.indexOf(mailTag[i])+mailTag[i].length(),handleStr.length());
    			break;
    		}
    	}
    	
    	//如果匹配数组为空
    	if(StringUtils.isBlank(mailStr)){
    		//邮箱正则
        	Pattern p=Pattern.compile("([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
            Matcher m=p.matcher(handleStr);  
            while(m.find()){  
            	mailStr = m.group();  
            }
    	}

    	return mailStr;
    }
    
    /**
     * 判断要处理字符串是否包含手机号，并返回手机号
     * @param handleStr
     * @return
     */
    public static String judgeMobile(String handleStr){
    	
    	//去除手机号两侧及中间空格
    	handleStr = handleStr.replace(" ", "");
    	//去除手机号中间所有-
    	handleStr = handleStr.replace("-", "");
    	//去除手机号中间所有.
    	handleStr = handleStr.replace(".", "");
    	
    	String mobileStr = "";
    	
    	//手机正则
    	//Pattern p=Pattern.compile("((13[0-9])|(14[5|7])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-8]))\\d{8}$"); //匹配电话号码
    	Pattern p=Pattern.compile("((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$"); //匹配电话号码
    	Matcher m=p.matcher(handleStr);  
        while(m.find()){  
        	mobileStr = m.group();  
        }
    	
    	return mobileStr;
    }
    
    /*public static void main(String[] args) {
		String mobielsa = "手机+8618112607966";
		System.out.println(mobielsa);
		System.out.println(judgeMobile(mobielsa));
	}*/
    
    /**
     * 调用名片全能王OCR
     * @param form
     * @return
     */
    @ApiOperation(value = "调用名片全能王OCR")
    @RequestMapping(value = "/scanCamCardOcr", method = RequestMethod.POST)
    public ResultData scanCamCardOcr(ScanCamCardOcrDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String filePath = form.getFilePath();  //图片地址
		
		if(StringUtils.isBlank(filePath)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		File myFile = new File(filePath);  //获取文件
		
		String pin = "zwlon1234567";  //设备识别码
		String user = "yu.yang@zlwon.com";  //个人资料中填写的公司邮箱
		String json = "1";  //设置为1时表示返回JSON格式结果，0则返回vCard格式结果，缺省时默认为0
		String pass = "6FD688BDTASAGXT5";  //通过申请后给您发送的通知邮件中API Key
		long size = myFile.length();  //jpg 文件的大小
		int lang = 7;  //需要识别的语言,代表识别英语，中文简体，中文繁体
		
		String actionUrl = "http://bcr1.intsig.net/BCRService/BCR_VCF2";  //上传文件的URL地址包括URL
		actionUrl = actionUrl+"?PIN="+pin+"&user="+user+"&pass="+pass+"&json="+json+"&lang="+lang+"&size="+size;
		
		Map<String, String> fileMap = new HashMap<String, String>();
		fileMap.put("upfile", filePath);
		
		String contentType = "image/jpeg";
		
		String result = formUpload(actionUrl,null,fileMap,contentType);
		
		return ResultData.one(result);
		
    }
    
    /**
     * 上传图片
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     * contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    @SuppressWarnings("rawtypes")
    public static String formUpload(String urlStr, Map<String, String> textMap,
    	Map<String, String> fileMap,String contentType) {
    	String res = "";
    	HttpURLConnection conn = null;
    	//boundary就是request头和上传文件内容的分隔符
    	String BOUNDARY = "---------------------------123821742118716"; 
    	try {
    		URL url = new URL(urlStr);
    		conn = (HttpURLConnection) url.openConnection();
    		conn.setConnectTimeout(5000);
    		conn.setReadTimeout(30000);
    		conn.setDoOutput(true);
    		conn.setDoInput(true);
    		conn.setUseCaches(false);
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Connection", "Keep-Alive");
    		conn.setRequestProperty("User-Agent",
    				"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
    		conn.setRequestProperty("Content-Type",
    				"multipart/form-data; boundary=" + BOUNDARY);
    		OutputStream out = new DataOutputStream(conn.getOutputStream());
    		
    		// text
    		if (textMap != null) {
    			StringBuffer strBuf = new StringBuffer();
    			Iterator iter = textMap.entrySet().iterator();
    			while (iter.hasNext()) {
    				Map.Entry entry = (Map.Entry) iter.next();
    				String inputName = (String) entry.getKey();
    				String inputValue = (String) entry.getValue();
    				if (inputValue == null) {
    					continue;
    				}
    				strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
    				strBuf.append("Content-Disposition: form-data; name=\""
    						+ inputName + "\"\r\n\r\n");
    				strBuf.append(inputValue);
    			}
    			out.write(strBuf.toString().getBytes());
    		}
    		
    		// file
    		if (fileMap != null) {
    			Iterator iter = fileMap.entrySet().iterator();
    			while (iter.hasNext()) {
    				Map.Entry entry = (Map.Entry) iter.next();
    				String inputName = (String) entry.getKey();
    				String inputValue = (String) entry.getValue();
    				if (inputValue == null) {
    					continue;
    				}
    				File file = new File(inputValue);
    				String filename = file.getName();
    				
    				//没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
    				contentType = new MimetypesFileTypeMap().getContentType(file);
    				//contentType非空采用filename匹配默认的图片类型
    				if(!"".equals(contentType)){
    					if (filename.endsWith(".png")) {
    						contentType = "image/png"; 
    					}else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
    						contentType = "image/jpeg";
    					}else if (filename.endsWith(".gif")) {
    						contentType = "image/gif";
    					}else if (filename.endsWith(".ico")) {
    						contentType = "image/image/x-icon";
    					}
    				}
    				
    				if (contentType == null || "".equals(contentType)) {
    					contentType = "application/octet-stream";
    				}
    				StringBuffer strBuf = new StringBuffer();
    				strBuf.append("\r\n").append("--").append(BOUNDARY)
    					.append("\r\n");
    				strBuf.append("Content-Disposition: form-data; name=\""
    						+ inputName + "\"; filename=\"" + filename
    						+ "\"\r\n");
    				strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
    				out.write(strBuf.toString().getBytes());
    				DataInputStream in = new DataInputStream(
    						new FileInputStream(file));
    				int bytes = 0;
    				byte[] bufferOut = new byte[1024];
    				while ((bytes = in.read(bufferOut)) != -1) {
    					out.write(bufferOut, 0, bytes);
    				}
    				in.close();
    			}
    		}
    		
    		byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
    		out.write(endData);
    		out.flush();
    		out.close();
    		// 读取返回数据
    		StringBuffer strBuf = new StringBuffer();
    		BufferedReader reader = new BufferedReader(new InputStreamReader(
    				conn.getInputStream()));
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			strBuf.append(line).append("\n");
    		}
    		res = strBuf.toString();
    		reader.close();
    		reader = null;
    	} catch (Exception e) {
    		System.out.println("发送POST请求出错。" + urlStr);
    		e.printStackTrace();
    	} finally {
    		if (conn != null) {
    			conn.disconnect();
    			conn = null;
    		}
    	}
    	return res;
   	}
    
    /**
     * 调用腾讯云OCR
     * @param form
     * @return
     */
    @ApiOperation(value = "调用腾讯云OCR")
    @RequestMapping(value = "/scanTencentOcr", method = RequestMethod.POST)
    public ResultData scanTencentOcr(ScanTencentOcrDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String appid = form.getAppid();  //项目ID
		String bucket = form.getBucket();  //图片空间
		Integer retImage = form.getRetImage();  //0 不返回图片，1 返回图片
		String[] urlList = form.getUrlList();  //图片 url 列表
		
		if(StringUtils.isBlank(appid) || StringUtils.isBlank(bucket) || retImage == null || urlList == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		BufferedReader reader = null;    
        String result = null;    
        StringBuffer sbf = new StringBuffer();
		
        String secretId = "AKIDRmz2vd4WgjbItIiZTClBQVQ9i5IKftRp";
        String secretKey = "LXZTFsLdjdM5e2EE3g8PkvwmQtGSSH1Z";
        
		try{
        	//post参数
			Map<String,Object> params = new HashMap<String,Object>();
        	params.put("appid", appid);
        	params.put("bucket", bucket);
        	params.put("ret_image", retImage);
        	params.put("url_list", urlList);
        	JSONObject jsonObject = new JSONObject(params);
        	String jsonParams = jsonObject.toString();
        	Integer jsonLength = jsonParams.getBytes().length;
        	
            //生成签名
        	String sign = TencentUtils.appSign(Long.valueOf(appid), secretId, secretKey, bucket, Long.valueOf(0));
        	
        	//得到URL对象
        	URL psotUrl = new URL("http://service.image.myqcloud.com/ocr/namecard");    
            //打开连接
        	HttpURLConnection connection = (HttpURLConnection) psotUrl    
                    .openConnection();
        	//设置提交类型 
            connection.setRequestMethod("POST"); 
            //设置标题头
            connection.setRequestProperty("Host","service.image.myqcloud.com");
            connection.setRequestProperty("Content-Length",jsonLength.toString());  //整个请求包体内容的总长度，单位：字节（Byte）
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Authorization",sign);  //鉴权签名
            //设置允许写出数据,默认是不允许 false 
            connection.setDoOutput(true); 
            connection.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true    
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonParams.getBytes(),0,jsonLength);
            
            InputStream is = connection.getInputStream();    
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));    
            String strRead = null;    
            while ((strRead = reader.readLine()) != null) {    
                sbf.append(strRead);  
            }    
            reader.close();    
            result = sbf.toString(); 
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
		
		return ResultData.one(result);
		
    }
}
