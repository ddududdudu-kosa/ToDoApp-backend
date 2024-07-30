package com.todo.member.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todo.config.security.dto.CustomUserDetails;
import com.todo.member.model.JoinDTO;
import com.todo.member.model.MemberDTO;
import com.todo.member.model.UserProfile;
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
	
	
    @GetMapping("/relevant")
    public ResponseEntity<List<UserProfile>> getRelevantUsers(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); 
        Long currentUserId = getMember(userDetails.getUsername()).getId();
        
        List<UserProfile> users = memberService.getRelevantUserProfiles(currentUserId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
	

    private MemberDTO getMember(String userEmail) {
    	return memberService.findByEmail(userEmail);
    }
}
