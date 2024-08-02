package com.todo.categorys.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * 작성자 : 김종현
 * 기능 : category
 * */

@Getter
@Builder
public class Category {
	private Long id;
    private String contents;
    private Long order;
    private String color;
    private Date createAt;
    private Date updateAt;
    private Long memberId;
}
