package com.zlwon.constant;

public enum IntegrationDeatilCode {
	
	INSERT_QUESTION(10001,"新增提问"),
	INSERT_ANSWER(10002,"新增回答");
	
	private Integer code;
    private String message;
    
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	private IntegrationDeatilCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
