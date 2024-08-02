package com.todo.member.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todo.member.model.JoinDTO;
import com.todo.member.model.MemberDTO;
import com.todo.member.model.UserProfile;
/**
* @packageName    : com.todo.member.service
* @fileName        : MemberService.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
public interface MemberService {

	// 회원가입
	void insertMember(MultipartFile file, JoinDTO joinDTO) throws IOException ;
	
	// 이메일로 계정정보 가져오기
	MemberDTO findByEmail(String email);
	
	List<UserProfile> getRelevantUserProfiles(Long userId);
}
