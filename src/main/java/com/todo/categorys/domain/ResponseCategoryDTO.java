package com.todo.categorys.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * 작성자 : 김종현
 * 기능 : category
 * */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseCategoryDTO {
	private Long id;
	private String contents;
	private Long order;
	private String color;
	private Date createAt;
	private Date updateAt;
	private Long memberId;
	
	// Long 타입 id만을 매개변수로 받는 생성자 추가
    public ResponseCategoryDTO(Long id) {
        this.id = id;
    }
}
