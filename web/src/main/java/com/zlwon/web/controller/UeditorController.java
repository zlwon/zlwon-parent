package com.zlwon.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.web.config.ueditorConfig.ActionEnter;

@RestController
public class UeditorController {

	@Autowired
	private ActionEnter actionEnter;
	
	/**
     * 富文本上传文件
     * @param request
     * @return
     */
	@RequestMapping("/ueditor/exec")
	public String exe(HttpServletRequest request){
		return actionEnter.exec(request);
	}
	
}
