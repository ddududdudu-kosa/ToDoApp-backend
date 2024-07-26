package com.todo.categorys.domain;

import java.util.Date;

import lombok.Data;

@Data
public class RequestCategoryDTO {
	private String contents;
    private Long order;
    private String color;
    private Long memberId;
}
