package com.zlwon.api.config;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rest.ResultData;
import com.zlwon.utils.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionHandleAdvice {

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultData errorHandler(Exception ex) {
    	log.error(ExceptionUtil.getStackTrace(ex));
        return ResultData.error(StatusCode.SYS_ERROR);
    }

    /**
     * 拦截捕捉自定义异常 CommonException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public ResultData myErrorHandler(CommonException ex) {
    	log.error(ExceptionUtil.getStackTrace(ex));
        return ResultData.error(ex.getCode(), ex.getMessage());
    }
}
