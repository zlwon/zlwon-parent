package com.zlwon.rest;

import com.zlwon.constant.StatusCode;

/**
 * Created by fred on 2017/12/5.
 */
public class ResultData<T> extends AbstractResult {
    private T data;

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultData<T> ok() {
        return new ResultData(StatusCode.SUCCESS);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultData<T> error() {
        return new ResultData(StatusCode.SYS_ERROR);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultData<T> error(StatusCode statusCode) {
        return new ResultData(statusCode);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultData<T> error(String code, String message) {
        return new ResultData(code, message);
    }

    public ResultData(StatusCode statusCode) {
        super(statusCode);
    }

    public ResultData(String code, String message) {
        super(code, message);
    }

    public static <T> ResultData<T> one(T obj) {
        ResultData<T> res = new ResultData(StatusCode.SUCCESS);
        res.data = obj;
        return res;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
