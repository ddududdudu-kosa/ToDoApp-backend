package com.todo.member.model;

import java.sql.Date;

import lombok.Data;

/**
* @packageName    : com.todo.member.model
* @fileName        : MemberRequest.java
* @author        : leejongseop
* @date            : 2024.07.23
* @description            :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.23        leejongseop       최초 생성
*/

@Data
public class MemberRequest {

	private String privacy;
	private String email;
	private String nickname;
	private String password;
	private String intro;
	private String profileImg;
	private String status;
	

}
