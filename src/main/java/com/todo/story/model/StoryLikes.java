package com.todo.story.model;

import java.sql.Date;

import lombok.Data;

@Data
public class StoryLikes {

	private Long id; 
	private Date createAt; 
	private Long memberId;
	private Long storiesId;
}