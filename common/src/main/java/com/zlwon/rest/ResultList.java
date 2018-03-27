package com.zlwon.rest;

import com.zlwon.constant.StatusCode;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by fred on 2017/12/5.
 */
public class ResultList<T> extends AbstractResult {
    private List<T> dataList; //列表数据
    private Integer totalPage; //总页数
    private Integer totalNumber;//总条数
    private Integer pageIndex; //当前页
    private Integer pageSize;  //当前页条数

    public static <T> ResultList<T> error() {
        return new ResultList(StatusCode.SYS_ERROR);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultList<T> error(StatusCode statusCode) {
        return new ResultList(statusCode);
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultList<T> list(Page<T> datas) {
        ResultList<T> res =  new ResultList(StatusCode.SUCCESS);
        res.dataList = datas.getContent();
        res.totalPage = datas.getTotalPages() + 1;
        res.totalNumber = (int)datas.getTotalElements();
        res.pageIndex = datas.getNumber() + 1;
        res.pageSize = datas.getSize();
        return res;
    }

    public ResultList(StatusCode statusCode) {
        super(statusCode);
    }

    public ResultList(String code, String message) {
        super(code, message);
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
