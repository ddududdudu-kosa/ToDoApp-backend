package com.todo.categorys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.todo.categorys.domain.Category;
import com.todo.categorys.domain.ResponseCategoryDTO;

@Mapper
public interface CategoryMapper {
	Long insertCategory(Category category);
	List<Category> selectCategoriesByMemberId(@Param("memberId") Long id);
	Long deleteCategory(Long categoryId);
	Long updateCategory(Category category);
	Long findMaxOrder(Long memberId);
	void updateOrderOnDelete(@Param("memberId") Long memberId, @Param("order") Long order);
	Category findByCategoryId(Long categoryId);
}
