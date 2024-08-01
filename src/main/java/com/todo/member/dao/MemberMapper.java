package com.todo.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.todo.member.model.JoinDTO;
import com.todo.member.model.MemberDTO;
import com.todo.member.model.UserProfile;

@Mapper
public interface MemberMapper {

	// 이메일로 멤버 찾기, 이메일 중복 여부 확인 용도
	MemberDTO findByEmail(String email);
	
	// 회원가입
	void insertMember(JoinDTO joinDTO);
	
	// 리스트
	List<UserProfile> findRelevantUsers(@Param("currentUserId") Long currentUserId);
	}