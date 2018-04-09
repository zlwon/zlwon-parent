package com.zlwon.vo.pc.answer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 提问回答详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AnswerDetailVo {

	private Integer id;
	
	private Integer uid;  //用户ID
	
	private String nickname;  //昵称
	
	private String headerimg;  //用户头像
	
	private Integer qid;  //问题ID
	
	private String content;  //回答内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
}
