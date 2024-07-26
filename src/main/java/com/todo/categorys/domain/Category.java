package com.todo.categorys.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
