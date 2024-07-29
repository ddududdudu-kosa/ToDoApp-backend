package com.todo.member.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todo.member.model.JoinDTO;
import com.todo.member.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	MemberService memberService;
	
	// 회원가입
	@PostMapping("join")
	public ResponseEntity<?> userJoin(@RequestPart("JoinDTO") JoinDTO joinDTO, @RequestPart("profile") MultipartFile file) throws IOException{
		memberService.insertMember(file, joinDTO);
		return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body("회원가입 완료");
	}
	
	
}
