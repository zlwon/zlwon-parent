package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SpecificationParameter implements  Serializable{
    private Integer id;

    private String name;

    private Integer classType;//类别：商标1、基材2、填充物3、安规认证5、应用行业6、应用市场7，终端客户8，应用产品9
    
    private Integer parentId;//父级ID，可代表别表ID，根据类型判断(商标父级是企业用户，应用行业是应用市场的上级,安规认证也有上级)

}