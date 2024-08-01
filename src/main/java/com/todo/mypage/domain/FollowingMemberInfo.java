package com.todo.mypage.domain;

import lombok.Data;

@Data
public class FollowingMemberInfo {

	private Long id;
	private String email;
	private String nickname;
	private String rating;
	private String intro;
	private String profileImg;
}