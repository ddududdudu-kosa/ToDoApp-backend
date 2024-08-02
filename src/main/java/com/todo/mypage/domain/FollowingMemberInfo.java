package com.todo.mypage.domain;

import lombok.Data;
/**
* @packageName    : com.todo.mypage.domain
* @fileName        : FollowingMemberInfo.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
@Data
public class FollowingMemberInfo {

	private Long id;
	private String email;
	private String nickname;
	private String rating;
	private String intro;
	private String profileImg;
}