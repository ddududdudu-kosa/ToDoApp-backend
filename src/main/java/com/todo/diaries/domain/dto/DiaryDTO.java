package com.todo.diaries.domain.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryDTO {

    private Long id;
    private String title;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dDate;
    private Integer temperature = 36;
    private String diaryimg = "";
    private String status;
    private Timestamp createat;
    private Timestamp updateat; 
    private String emoji = "";
    private Long memberId;


    // Getters and Setters
}
