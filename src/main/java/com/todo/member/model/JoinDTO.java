package com.todo.member.model;

import lombok.Data;

@Data
public class JoinDTO {

	private String role;
	private String privacy;
	private String email;
	private String nickname;
	private String password;
	private String intro;
	private String profileImg;
	private String status;
}
