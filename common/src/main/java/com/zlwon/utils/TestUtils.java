package com.zlwon.utils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 测试工具类
 * @author yangy
 *
 */

public class TestUtils {

	/**
	 * 访问远程的一个服务器的文件是否存在
	 * @param path  远程服务器文件路径
	 * @return
	 */
	public static boolean judgePath(String path) {
		
		boolean flag = false;
		
		try {
			URL url = new URL(path);
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			String message = urlcon.getHeaderField(0);
			//文件存在‘HTTP/1.1 200 OK’ 文件不存在 ‘HTTP/1.1 404 Not Found’
			String reponse = urlcon.getResponseMessage();  //获取响应信息
			//文件存在 ‘OK’  文件不存在 ‘Not Found’
			int reponseCode = urlcon.getResponseCode();  //获取响应状态码
			//文件存在 200  文件不存在 404
			if(reponseCode == 200){
				flag = true;
			}else{
				flag = false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static void main(String[] args) {
		judgePath("http://118.89.142.11:9000/download/images/20160605102037.jpg");
	}
}
