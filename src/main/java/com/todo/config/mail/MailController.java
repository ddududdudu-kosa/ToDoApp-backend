package com.todo.config.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MailController {

	@Autowired
	MailService mailService;
	
	// 이메일 인증
	@PostMapping("api/mailConfirm")
	public String mailConfirm(@RequestBody EmailRequest email) throws Exception {
		log.info("넘어온 이메일 : {}",email.toString());
		String code = mailService.sendSimpleMessage(email.getEmail());
		System.out.println("인증코드 : " + code);
		return code;
	}
}
