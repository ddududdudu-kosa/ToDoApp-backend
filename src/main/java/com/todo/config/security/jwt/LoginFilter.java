package com.todo.config.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.member.model.LoginDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
/**
* @packageName    : com.todo.config.security.jwt
* @fileName        : LoginFilter.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		// 클라이언트 요청에서 username, password 추출
//		String username = obtainUsername(request);
//		String password = obtainPassword(request);
		LoginDTO loginDTO = new LoginDTO();

		try {
		    ObjectMapper objectMapper = new ObjectMapper();
		    ServletInputStream inputStream = request.getInputStream();
		    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		    loginDTO = objectMapper.readValue(messageBody, LoginDTO.class);

		} catch (IOException e) {
		    throw new RuntimeException(e);
		}

		System.out.println(loginDTO.getEmail());

		String username = loginDTO.getEmail();
		String password = loginDTO.getPassword();

		System.out.println(username);
		
		// 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

		// token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(authToken);
	}

	// 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) {

	    // 유저 정보
	    String username = authentication.getName();  
	    // role값 정보
	    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
	    GrantedAuthority auth = iterator.next();
	    String role = auth.getAuthority();

	    //토큰 생성  
	    String access = jwtUtil.createJwt(username, role, 24 * 60 * 60 * 1000L); 
	    //응답 설정
	    response.setHeader("access", access);
	    response.setStatus(HttpStatus.OK.value());
	}

	// 로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) {

		//로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
	}
	
}
