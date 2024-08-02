package com.todo.config.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.member.model.MemberDTO;
import com.todo.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;
/**
* @packageName    : com.todo.config.mail
* @fileName        : EmailController.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
@Slf4j
@RestController
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	MemberService memberService;
	
	// 이메일 인증
	@PostMapping("api/mail/confirm")
	public ResponseEntity<?> mailConfirm(@RequestBody EmailRequest email) throws Exception {
		log.info("넘어온 이메일 : {}",email.toString());
		String code = emailService.sendSimpleMessage(email.getEmail());
		System.out.println("인증코드 : " + code);
		return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body("인증 코드 발송을 완료했습니다.");
	}
	
	// 이메일 인증 코드 확인
	@GetMapping("api/mail/codeConfirm")
	public ResponseEntity<?> mailCodeConfirm(@RequestBody EmailRequest email) throws Exception {
		boolean check = emailService.emailCodeConfirm(email.getEmail(), email.getCode());
		if(check) {
			return ResponseEntity.status(HttpStatus.OK)
					.header("Content-Type", "text/plain; charset=UTF-8")
					.body("인증완료");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body("인증실패");
		
	}
	
	// 이메일 중복 여부 확인
	@PostMapping("api/mail/exist")
	public ResponseEntity<?> mailExist(@RequestBody EmailRequest email) throws Exception {
		log.info("넘어온 이메일 : {}", email);
		MemberDTO member = memberService.findByEmail(email.getEmail());
		if (member == null) {
			return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body("사용해도 좋은 이메일입니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Content-Type", "text/plain; charset=UTF-8")
				.body("이미 사용중인 이메일입니다.");
	}
}
