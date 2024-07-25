package com.todo.member.service;

import com.todo.member.model.Member;

/**
* @packageName    : com.todo.member.service
* @fileName        : MemberService.java
* @author        : leejongseop
* @date            : 2024.07.23
* @description            :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.23        leejongseop       최초 생성
*/
public interface MemberService {

	// 회원가입
	void insertMember(Member member);
	
	// 이메일 중복 여부 체크용
	int findByEmail(String email);
}
