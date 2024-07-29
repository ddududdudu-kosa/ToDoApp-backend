package com.todo.member.model;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberDTO {

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
	
}
