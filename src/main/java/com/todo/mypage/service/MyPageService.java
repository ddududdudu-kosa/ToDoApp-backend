package com.todo.mypage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.todo.config.security.dto.CustomUserDetails;
import com.todo.member.dao.MemberMapper;
import com.todo.member.model.MemberDTO;
import com.todo.mypage.domain.FollowStats;
import com.todo.mypage.domain.FollowingMemberInfo;
import com.todo.mypage.domain.MemberInfo;
import com.todo.mypage.domain.MyStats;
import com.todo.mypage.mapper.MyPageMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyPageService {

	@Autowired
	MyPageMapper myPageMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	public static List<String> saying = new ArrayList<>();
	
	static {
		saying.add("명언1");
		saying.add("명언2");
		saying.add("명언3");
		saying.add("명언4");
		saying.add("명언5");
	}
	
	// 나의 정보 및 친구 정보
	public Map<String, Object> getMyInfo(Authentication authentication){
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		MemberDTO member = memberMapper.findByEmail(email);
		log.info("이메일 : {}", email);
		
		
        MemberInfo myInfo = myPageMapper.getMyInfo(member.getId());
        List<FollowingMemberInfo> myFollowsInfo= myPageMapper.getMyFollowsInfo(member.getId());
		
		log.info("나의 정보 : {}", myInfo);
		log.info("나의 친구 정보 : {}", myFollowsInfo);
		
		Map<String, Object> result = new HashMap<>();
		result.put("myInfoList", myInfo);
		result.put("myFollowsInfoList", myFollowsInfo);
		return result;
	}
	
	// 나의 통계 정보 및 친구 통계 정보
	public Map<String, Object> getMyStatsInfo(Authentication authentication){
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		MemberDTO member = memberMapper.findByEmail(email);
		log.info("이메일 : {}", email);
		
		
        MyStats myStats = myPageMapper.getMyStatsInfo(member.getId());
        FollowStats followStats = myPageMapper.getMyFollowStatsInfo(member.getId());
		
		log.info("나의 통계 정보 : {}", myStats);
		log.info("친구 통계 정보 : {}", followStats);
		
		Map<String, Object> result = new HashMap<>();
		result.put("myStats", myStats);
		if(followStats == null) {
			result.put("myFollowStats", "현재 등록된 친구가 없습니다.");
		}else {
			result.put("myFollowStats", followStats);			
		}
		return result;
	}
	
	// 명언
	public String getSaying() {
		Random random = new Random();
        int randomNumber = random.nextInt(saying.size()); // 0부터 n-1까지의 랜덤 숫자 생성
		return saying.get(randomNumber);
	}
}
