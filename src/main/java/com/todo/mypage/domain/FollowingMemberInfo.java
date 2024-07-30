package com.todo.mypage.domain;

import lombok.Data;

@Data
public class FollowingMemberInfo {

	private Integer id;
	private String email;
	private String rating;
	private String intro;
	private String profileImg;
}
