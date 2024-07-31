package com.todo.mypage.domain;

import lombok.Data;

@Data
public class TodayComplete {

	// 오늘 목표한 것
	private Integer today;
	
	// 오늘 목표한 것들 중 달성한 것
	private Integer complete;
}
