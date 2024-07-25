package com.todo.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.member.model.Member;
import com.todo.member.model.MemberRequest;
import com.todo.member.service.MemberService;

/**
* @packageName    : com.todo.member.controller
* @fileName        : MemberController.java
* @author        : leejongseop
* @date            : 2024.07.23
* @description            :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.07.23        leejongseop       최초 생성
*/

@RestController
@RequestMapping("user")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/join")
	public void insertMember(@RequestBody MemberRequest memberRequest) {
		memberService.insertMember(new Member(memberRequest));
	}
	
	@GetMapping("/emailConfirm")
	public ResponseEntity<?> findByEmail(@RequestBody String email) {
		return ResponseEntity.ok(memberService.findByEmail(email));
	}
}
