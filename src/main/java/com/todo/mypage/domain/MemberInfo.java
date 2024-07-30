package com.todo.mypage.domain;

import lombok.Data;

@Data
public class MemberInfo {

	private Long id;
	private String rating;
	private String privacy;
	private String email;
	private String nickname;
	private String intro;
	private String profileImg;
}
