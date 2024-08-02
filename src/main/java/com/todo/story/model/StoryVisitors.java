package com.todo.story.model;

import java.sql.Date;

import lombok.Data;

/*
 * 작성자 : 전민재
 * 기능 : story
 * */

@Data
public class StoryVisitors {

	private Long id; 
	private Date createAt; 
	private Long memberId;
	private Long storiesId;
}
