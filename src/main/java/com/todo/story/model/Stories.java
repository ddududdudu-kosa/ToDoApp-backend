package com.todo.story.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Stories {

	private Long id;
	private String title;
	private String storyImg;
	private Integer totalLikes;
	private Long memberId;
	private Date createAt; 
}
