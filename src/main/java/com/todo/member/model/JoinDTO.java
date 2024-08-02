package com.todo.member.model;

import lombok.Data;
/**
* @packageName    : com.todo.member.model
* @fileName        : JoinDTO.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
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
