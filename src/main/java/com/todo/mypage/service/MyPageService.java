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
import com.todo.mypage.domain.Top5Member;
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
		saying.add("자신이 되고 싶은 대로 될 수 있다. 다만 그 길을 가야 한다. - 스티브잡스 (Steve Jobs)");
		saying.add("성공은 우연히 이루어지지 않는다. 그것은 열정, 노력, 그리고 끊임없는 학습의 결과물이다. - 콜린 파월 (Colin Powell)");
		saying.add("당신이 될 수 있는 것보다 더 나은 것이 무엇인지 알 수 없다. - 알버트 아인슈타인 (Albert Einstein)");
		saying.add("어제의 나와 오늘의 내가 다르다면, 그것은 성장의 증거이다. - 존 C. 맥스웰 (John C. Maxwell)");
		saying.add("시작이 반이다. 그러니 오늘이 바로 당신의 새로운 시작일 수 있다. - 앨버트 슈바이처 (Albert Schweitzer)");
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
	
	public Map<String, Object> getTodayComplete(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		MemberDTO member = memberMapper.findByEmail(email);
		
		int today = myPageMapper.getPurposeCount(member.getId());
		int complete = myPageMapper.getCompleteCount(member.getId());
		Map<String, Object> result = new HashMap<>();
		result.put("today", today);
		result.put("complete", complete);
		return result;
	}
	
	public List<Top5Member> getTop5(){
		return myPageMapper.getTop5();
	}
}
