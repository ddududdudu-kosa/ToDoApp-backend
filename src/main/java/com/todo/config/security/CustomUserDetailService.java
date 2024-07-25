package com.todo.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.error.exception.ErrorCode;
import com.todo.error.exception.NotFoundException;
import com.todo.member.dao.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

	private final MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomUserDetail user = memberMapper.findAccount(email)
				.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
		return user;
	}

}
