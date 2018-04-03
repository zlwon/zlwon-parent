package com.zlwon.pc.config;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rest.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return ResultData.error(StatusCode.SYS_ERROR.getCode(), ex.getMessage());
    }

    /**
     * 拦截捕捉自定义异常 CommonException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public ResultData myErrorHandler(CommonException ex) {
        return ResultData.error(ex.getCode(), ex.getMessage());
    }
}
