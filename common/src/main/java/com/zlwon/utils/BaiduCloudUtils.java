package com.zlwon.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/**
 * 百度云工具类
 * @author yangy
 *
 */

public class BaiduCloudUtils {

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
    
    /**
     * 文本审核接口（暂不能使用）
     * @param commandNo  策略定制标识，可支持线下渠道定制，通用默认值为500071
     * @param content  待审核的文本内容，不可为空，长度不超过20000字节
     * @return
     * errno	int	服务内部计算返回标识，无异常时都为0，-1表示command_no为空，-2表示content为空，-3表示command_no暂不支持
     * spam	int	请求中是否包含违禁，0表示非违禁，1表示违禁，2表示建议人工复审
     * labels  array	请求中的违禁类型集合，一个或多个，审核通过则为空
     * hit	array	命中的违禁词集合，可能为空.1暴恐违禁,2文本色情,3政治敏感,4恶意推广,5低俗辱骂
     * logid	array	正确调用生成的唯一标识码，用于问题定位
     * message	array	错误信息，仅在报错时会出现
     */
    public static String textSensitive(String commandNo,String content){
    	
    	String apiKey = "fRPbVY22OpBoInGYav8zwpWb";
    	String secretKey  = "ubiTE3V5QGGG8kFHD7GVnFGe58NmBe35";
    	
    	BufferedReader reader = null;    
        String result = null;    
        StringBuffer sbf = new StringBuffer();
    	
    	try{
    		//获取token
        	String accessToken = getAuth(apiKey,secretKey);
        	if(StringUtils.isBlank(accessToken)){
        		return "";
        	}
        	
        	//post参数
        	Map<String,Object> params = new LinkedHashMap<>();
        	params.put("command_no", commandNo);
        	params.put("content", content);
        	 
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
        	URL psotUrl = new URL("https://aip.baidubce.com/rest/2.0/antispam/v1/spam?access_token="+accessToken);    
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
        	
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return result;
    }
    
    public static void main(String[] args) {
    	System.out.println(textSensitive("500071","操你妈"));
	}
}
