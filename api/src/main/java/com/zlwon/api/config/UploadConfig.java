package com.zlwon.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component 
@ConfigurationProperties(prefix = "spring.upload", ignoreInvalidFields = false)
public class UploadConfig {
	private  String   domain;//域名或者ip
	private  String   domainPath;//nginx配置的路径(域名映射)
	private  String   filePath;//文件保存路径
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomainPath() {
		return domainPath;
	}
	public void setDomainPath(String domainPath) {
		this.domainPath = domainPath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
