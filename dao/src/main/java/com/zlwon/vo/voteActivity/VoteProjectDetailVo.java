package com.zlwon.vo.voteActivity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票活动参与项目信息详细出参
 * 该VO对象yuand也在使用，修改时，请转告
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class VoteProjectDetailVo implements Serializable {

	private Integer id;  //自增ID
	
	private Integer aid;  //活动表ID
	
	private String photo;  //图片
	
	private String title;  //信息标题
	
	private Integer uid;  //创建用户ID
	
	private String nickName;  //创建用户昵称
	
	private String headerimg;  //创建用户头像
	
	private Integer supportNums;  //点赞量
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
	
	private Integer examine; //用户创建数据审核结果，0未审核，1审核通过
}
