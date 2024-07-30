package com.todo.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.todo.member.model.JoinDTO;
import com.todo.member.model.MemberDTO;

@Mapper
public interface MemberMapper {

	// 이메일로 멤버 찾기, 이메일 중복 여부 확인 용도
	MemberDTO findByEmail(String email);
	
	// 회원가입
	void insertMember(JoinDTO joinDTO);
	
	
}
