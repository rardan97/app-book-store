package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.category.CategoryReq;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.service.CategoryService;
import com.blackcode.book_store_be.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getCategoryListAll")
    public ResponseEntity<ApiResponse<List<CategoryRes>>> getCategoryListAll(){
        List<CategoryRes> categoryResList = categoryService.getListAll();
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", 200, categoryResList));
    }

    @GetMapping("/getCategoryFindById/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> getCategoryFindById(@PathVariable("id") Long id){
        CategoryRes categoryRes = categoryService.getFindById(id);
        return ResponseEntity.ok(ApiResponse.success("Category found", 200, categoryRes));
    }

    @PostMapping("/addCategory")
    public ResponseEntity<ApiResponse<CategoryRes>> addCategory(@RequestBody CategoryReq categoryReq){
        CategoryRes categoryRes = categoryService.addCategory(categoryReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Category created", 201, categoryRes));
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryReq categoryReq){
        CategoryRes categoryRes = categoryService.updateCategory(id, categoryReq);
        return ResponseEntity.ok(ApiResponse.success("Category Update", 200, categoryRes));
    }

    @DeleteMapping("/deleteCategoryById/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategoryById(@PathVariable("id") Long id){
        String rtn = categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category success Delete", 200, rtn));
    }
}