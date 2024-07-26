package com.todo.categorys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.categorys.domain.Category;
import com.todo.categorys.domain.RequestCategoryDTO;
import com.todo.categorys.domain.ResponseCategoryDTO;
import com.todo.categorys.service.CategoryService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
@Log4j2
public class CategoryController {
	
	private final CategoryService categoryService;
	
	// 카테고리 내용 생성 로직
	@PostMapping
	public ResponseEntity<ResponseCategoryDTO> insertCategory(@RequestBody RequestCategoryDTO req){
	    Long maxOrder = categoryService.findMaxOrder(req.getMemberId()) + 1;
	    log.info("maxOrder : " + maxOrder);
	    Category category = Category.builder()
	            .contents(req.getContents())
	            .order(maxOrder)
	            .color(req.getColor())
	            .memberId(req.getMemberId())
	            .build();
	    Long savedCategoryId = categoryService.insertCategory(category);
	    ResponseCategoryDTO responseCategory = new ResponseCategoryDTO(savedCategoryId);
	    return ResponseEntity.status(HttpStatus.CREATED).body(responseCategory);
	}
	
	
	// 카테고리 계정별 카테고리 불러오기 로직
	@GetMapping("/{memberId}")
    public ResponseEntity<List<ResponseCategoryDTO>> getCategoriesByMemberId(@PathVariable("memberId") Long memberId) {
        List<Category> categories = categoryService.getCategoriesByMemberId(memberId);
        List<ResponseCategoryDTO> responseCategories = categories.stream()
            .map(cat -> ResponseCategoryDTO.builder()
                .id(cat.getId())
                .contents(cat.getContents())
                .order(cat.getOrder())
                .color(cat.getColor())
                .createAt(cat.getCreateAt())
                .updateAt(cat.getUpdateAt())
                .memberId(cat.getMemberId())
                .build())
            .collect(Collectors.toList());
        return ResponseEntity.ok(responseCategories);
    }
	
	// 카테고리 내용 업데이트 로직
	@PutMapping("/{id}")
	public ResponseEntity<ResponseCategoryDTO> updateCategory(@RequestBody RequestCategoryDTO requestCategory, @PathVariable("id") @Param("id") Long id) {
        Category category = Category.builder()
            .id(id)
            .contents(requestCategory.getContents())
            .order(requestCategory.getOrder())
            .color(requestCategory.getColor())
            .memberId(requestCategory.getMemberId())
            .build();
        	Category updatedCategory = categoryService.updateCategory(category);
        	ResponseCategoryDTO responseCategory = ResponseCategoryDTO.builder()
                    .id(updatedCategory.getId())
                    .contents(updatedCategory.getContents())
                    .order(updatedCategory.getOrder())
                    .color(updatedCategory.getColor())
                    .createAt(updatedCategory.getCreateAt())
                    .updateAt(updatedCategory.getUpdateAt())
                    .memberId(updatedCategory.getMemberId())
                    .build();
        	return ResponseEntity.ok(responseCategory);
    }
	
	// 카테고리 내용 삭제 로직(삭제후 순서도 수정)
	@Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
    	Category category = categoryService.findByCategoryId(id);
    	categoryService.deleteCategory(id);
    	categoryService.updateOrderOnDelete(category.getMemberId(), category.getOrder());
        return ResponseEntity.noContent().build();
    }
	
}
