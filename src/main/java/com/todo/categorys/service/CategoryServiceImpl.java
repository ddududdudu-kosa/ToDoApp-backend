package com.todo.categorys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.categorys.domain.Category;
import com.todo.categorys.mapper.CategoryMapper;
import lombok.AllArgsConstructor;

/*
 * 작성자 : 김종현
 * 기능 : category
 * */

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryMapper categoryMapper;

	@Override
	public Long insertCategory(Category category) {
		return categoryMapper.insertCategory(category);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		categoryMapper.deleteCategory(categoryId);
	}

	@Override
	public List<Category> getCategoriesByMemberId(Long memberId) {
		return categoryMapper.selectCategoriesByMemberId(memberId);
	}

	@Override
	public Category updateCategory(Category category) {
		Long affectedRows = categoryMapper.updateCategory(category);
	    if (affectedRows > 0) {
	        return categoryMapper.findByCategoryId(category.getId()); // ID로 검색하는 메소드가 있다고 가정
	    }
	    return null;
	}

	@Override
	public Long findMaxOrder(Long memberId) {
		return categoryMapper.findMaxOrder(memberId);
	}

	@Override
	public void updateOrderOnDelete(Long memberId, Long order) {
		categoryMapper.updateOrderOnDelete(memberId, order);
	}

	@Override
	public Category findByCategoryId(Long categoryId) {
		return categoryMapper.findByCategoryId(categoryId);
	}
}