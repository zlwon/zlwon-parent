package com.zlwon.dto.web.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * web端编辑资讯信息入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class UpdateInfoDto {
	
	private Integer id;  //资讯ID

	private String mainTitle;  //主标题

    private String subTitle;  //副标题

    private String source;  //来源
    
    private String content;  //资讯详情内容

    private String originPic;  //配图原图

    private String thumbPic;  //配图缩略图
}
