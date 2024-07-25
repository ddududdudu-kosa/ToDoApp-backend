package com.todo.member.model;

import java.sql.Date;

import lombok.Data;

/**
* @packageName    : com.todo.member.model
* @fileName        : Member.java
* @author        : leejongseop
* @date            : 2024.07.23
* @description            :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.23        leejongseop       최초 생성
*/

@Data
public class Member {
	
	private Integer id;
	private String role;
	private String rating;
	private String privacy;
	private String email;
	private String nickname;
	private String password;
	private String intro;
	private String profileImg;
	private String status;
	private Date createat;
	private Date updateat;
	
	public Member (MemberRequest memberRequest) {
		this.privacy = memberRequest.getPrivacy();
		this.email = memberRequest.getEmail();
		this.nickname = memberRequest.getNickname();
		this.password = memberRequest.getPassword();
		this.intro = memberRequest.getIntro();
		this.profileImg = memberRequest.getProfileImg();
		this.status = memberRequest.getStatus();
	}
}
