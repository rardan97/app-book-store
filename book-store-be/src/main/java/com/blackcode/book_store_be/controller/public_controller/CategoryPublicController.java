package com.blackcode.book_store_be.controller.public_controller;

import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.service.CategoryService;
import com.blackcode.book_store_be.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/category")
public class CategoryPublicController {

    private final CategoryService categoryService;

    public CategoryPublicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getCategoryListAll")
    public ResponseEntity<ApiResponse<List<CategoryRes>>> getBooksListAll(){
        List<CategoryRes> categoryResList = categoryService.getListAll();
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", 200, categoryResList));
    }

    @GetMapping("/getCategoryFindById/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> getBooksFindById(@PathVariable("id") Long id){
        CategoryRes categoryRes = categoryService.getFindById(id);
        return ResponseEntity.ok(ApiResponse.success("Category found", 200, categoryRes));
    }
}
