package com.todo.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.error.exception.CustomBaseException;
import com.todo.error.exception.ErrorCode;
import com.todo.member.dao.MemberMapper;
import com.todo.member.model.Member;

/**
* @packageName    : com.todo.member.service
* @fileName        : MemberServiceImpl.java
* @author        : leejongseop
* @date            : 2024.07.23
* @description            :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.23        leejongseop       최초 생성
*/

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 회원가입
	@Override
	public void insertMember(Member member) {
		member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
		memberMapper.insertMember(member);
	}

	// 이메일 중복 체크 여부
	@Override
	public int findByEmail(String email) {
		int count = memberMapper.findByEmail(email);
		if (count >= 1) throw new CustomBaseException(ErrorCode.ALREADY_EMAIL);
		return count;
	}

}
