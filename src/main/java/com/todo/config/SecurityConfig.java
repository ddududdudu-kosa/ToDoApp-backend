package com.todo.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.todo.config.security.jwt.JWTFilter;
import com.todo.config.security.jwt.JWTUtil;
import com.todo.config.security.jwt.LoginFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	// AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	// AuthenticationManager Bean 등록
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	// 비밀번호 해싱 저장
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// cors설정
		http.cors((cors -> cors.configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

				CorsConfiguration configuration = new CorsConfiguration();

				configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
				configuration.setAllowedMethods(Collections.singletonList("*"));
				configuration.setAllowCredentials(true);
				configuration.setAllowedHeaders(Collections.singletonList("*"));
				configuration.setMaxAge(3600L);

				configuration.setExposedHeaders(Collections.singletonList("Access"));

				return configuration;
			}
		})));

		// csrf disable
		http.csrf((auth) -> auth.disable());

		// Form 로그인 방식 disable
		http.formLogin((auth) -> auth.disable());

		// http basic 인증 방식 disable
		http.httpBasic((auth) -> auth.disable());

		// 경로별 인가 작업
		http
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/login", "/", "/join","/api/mail/**").permitAll()	// /login, /, /join 경로는 모두에게 허용
					.requestMatchers("/diary/register").authenticated() 	// 추가
					.requestMatchers("/member/profile").authenticated() // /member/profile 경로는 인증된 사용자만 허용
					.requestMatchers("/admin/**").hasRole("ADMIN")			// /admin 경로에는 ADMIN권한만 가진 계정만 허용
					.anyRequest().authenticated());						// 그 외의 모든 경로에는 로그인한 사용자만 허용

//					.anyRequest().permitAll());
		// JWTFilter 등록 (로그인 필터 앞에다 등록)
		http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

		// 필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에
		// authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
		http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
				UsernamePasswordAuthenticationFilter.class);

		// 세션 설정 (jwt에서는 필수)
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
