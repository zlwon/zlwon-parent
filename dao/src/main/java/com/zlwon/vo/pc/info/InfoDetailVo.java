package com.zlwon.vo.pc.info;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 资讯信息详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InfoDetailVo {

	private Integer id;   //自增ID

    private String mainTitle;  //主标题

    private String subTitle;  //副标题

    private String source;  //来源

    private Integer readNum;  //阅读点击数
    
    private String content;  //资讯详情内容

    private String originPic;  //配图原图

    private String thumbPic;  //配图缩略图

    private Integer isHot;  //是否热门  0：否，1：是

    private Integer uid;  //创建人（后台系统添加默认为0）

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;  //创建时间
}
