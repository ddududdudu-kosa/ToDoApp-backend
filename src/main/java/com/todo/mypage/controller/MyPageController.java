package com.todo.mypage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.todo.config.security.dto.CustomUserDetails;
import com.todo.member.dao.MemberMapper;
import com.todo.mypage.service.MyPageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MyPageController {

	@Autowired
	MyPageService myPageService;
	
	
	@GetMapping("/mypage")
	public ResponseEntity<?> getMyInfo(Authentication authentication){
		Gson gson = new Gson();
		Map<String, Object> info = myPageService.getMyInfo(authentication);
		return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body(gson.toJson(info));
	}
	
	@GetMapping("/mypage/follower/graph")
	public ResponseEntity<?> getMyStatsInfo(Authentication authentication){
		Gson gson = new Gson();
		Map<String, Object> stats = myPageService.getMyStatsInfo(authentication);
		return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body(gson.toJson(stats));
	}
	
	@GetMapping("/mypage/saying")
	public ResponseEntity<?> getSaying(){
		return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body(myPageService.getSaying());
	}
}
