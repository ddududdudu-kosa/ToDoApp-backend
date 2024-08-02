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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.config.security.dto.CustomUserDetails;
import com.todo.member.model.JoinDTO;
import com.todo.member.model.MemberDTO;
import com.todo.member.model.UserProfile;
import com.todo.member.service.MemberService;
/**
* @packageName    : com.todo.member.controller
* @fileName        : MemberController.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
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
    
 // 사용자 프로필 정보 가져오기
    @GetMapping("/member/profile")
    public ResponseEntity<MemberDTO> getProfile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        MemberDTO memberDTO = getMember(userEmail);

        if (memberDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(memberDTO);
    }

}
