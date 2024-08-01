package com.todo.diaries.domain.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class DiaryDetail {
	private Long id;
    private Long memberId;
    private String nickname;
    private String profileImg;
    private String privacy;
    private String title; 
    private Date diaryDate;
    private String emoji;
    private Integer temperature;
}