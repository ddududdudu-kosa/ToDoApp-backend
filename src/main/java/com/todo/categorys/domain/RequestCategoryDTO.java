package com.todo.categorys.domain;

import java.util.Date;

import lombok.Data;

/*
 * 작성자 : 김종현
 * 기능 : category
 * */

@Data
public class RequestCategoryDTO {
	private String contents;
    private Long order;
    private String color;
    private Long memberId;
}
