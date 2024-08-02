package com.todo.categorys.service;

import java.util.List;

import com.todo.categorys.domain.Category;

/*
 * 작성자 : 김종현
 * 기능 : category
 * */

public interface CategoryService {
	Long insertCategory(Category category);
    void deleteCategory(Long categoryId);
    List<Category> getCategoriesByMemberId(Long memberId);
    Category updateCategory(Category category);
    // 현재 멤버별 카테고리 순서 구하기
    Long findMaxOrder(Long memberId);
    // 카테고리 삭제시 특정 유저의 카테고리 순서 갱신하는 메서드
    void updateOrderOnDelete(Long memberId, Long order);
	Category findByCategoryId(Long categoryId);
    
}
