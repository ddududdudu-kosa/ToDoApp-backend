package com.todo.member.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.member.model.JoinDTO;
import com.todo.member.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	MemberService memberService;
	
	// 회원가입
	@PostMapping("join")
	public ResponseEntity<?> userJoin(@RequestPart("JoinDTO") String joinDTOString, @RequestPart("profile") MultipartFile file) throws IOException{
		
		// JSON 데이터를 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JoinDTO joinDTO = objectMapper.readValue(joinDTOString, JoinDTO.class);
        
		memberService.insertMember(file, joinDTO);
		return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body("회원가입 완료");
	}
	
	
}
