package com.todo.member.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.todo.config.security.CustomUserDetail;
import com.todo.member.model.Member;

/**
* @packageName    : com.todo.member.dao
* @fileName        : MemberMapper.java
* @author        : leejongseop
* @date            : 2024.07.23
* @description            :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.23        leejongseop       최초 생성
*/

@Mapper
public interface MemberMapper {
	
	// 회원가입
	void insertMember(Member member);
	
	// 이메일 중복 여부 체크용
	int findByEmail(String email);
	
	// 로그인용
	Optional<CustomUserDetail> findAccount(String email);
	
}
