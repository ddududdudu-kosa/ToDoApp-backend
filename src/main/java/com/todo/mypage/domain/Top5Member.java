package com.todo.mypage.domain;

import lombok.Data;
/**
* @packageName    : com.todo.mypage.domain
* @fileName        : Top5Member.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
@Data
public class Top5Member {

	private String email;
	private String nickname;
	private Integer totalAchievementCount;
	private String profileImg;
	private Integer rating;
	private Long memberId;
}
