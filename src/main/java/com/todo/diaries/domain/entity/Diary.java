package com.todo.diaries.domain.entity;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diary {

 private Long id;
 private String title;
 private String contents;
 private Date dDate;
 private Integer temperature;
 private String diaryimg;
 private String status;
 private Timestamp createat;
 private Timestamp updateat;
 private String emoji;
 private Long memberId;

 // Getters and Setters
}
