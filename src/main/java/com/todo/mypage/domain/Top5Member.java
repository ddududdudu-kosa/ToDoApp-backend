package com.todo.mypage.domain;

import lombok.Data;

@Data
public class Top5Member {

	private String email;
	private String nickname;
	private Integer totalAchievementCount;
	private String profileImg;
	private Integer rating;
	private Long memberId;
}
