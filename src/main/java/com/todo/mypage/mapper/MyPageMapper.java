package com.todo.mypage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

import com.todo.mypage.domain.FollowStats;
import com.todo.mypage.domain.FollowingMemberInfo;
import com.todo.mypage.domain.MemberInfo;
import com.todo.mypage.domain.MyStats;

@Mapper
public interface MyPageMapper {

	// 나의 정보 가져오기
	MemberInfo getMyInfo(@Param("memberId") int memberId);
	
	// 친구 정보 가져오기
	List<FollowingMemberInfo> getMyFollowsInfo(@Param("memberId") int memberId);
	
	// 나의 통계 정보 및 친구 통계 정보 가져오기
	MyStats getMyStatsInfo(@Param("memberId") int memberId);
	
	// 친구 통계 정보 가져오기
	FollowStats getMyFollowStatsInfo(@Param("memberId") int memberId);
}
