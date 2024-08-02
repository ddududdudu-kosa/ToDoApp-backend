package com.todo.mypage.domain;

import lombok.Data;
/**
* @packageName    : com.todo.mypage.domain
* @fileName        : MemberInfo.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
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
