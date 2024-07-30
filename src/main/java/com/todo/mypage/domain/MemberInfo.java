package com.todo.mypage.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberInfo {

	private Integer id;
	private String rating;
	private String privacy;
	private String email;
	private String nickname;
	private String intro;
	private String profileImg;
}
