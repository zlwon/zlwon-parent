package com.zlwon.vo.specificationParameter;

import lombok.Data;

/**
 * web端安规认证vo
 * @author yuand
 *
 */
@Data
public class SafetyParameterVo {

	private  Integer  id;//安规认证一级子类标签id
	
	private  String   fname;//安规认证一级标签名称
	
	private  String   sname;//安规认证一级子类标签名称
}
