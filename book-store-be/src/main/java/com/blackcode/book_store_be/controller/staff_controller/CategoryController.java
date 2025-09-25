package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.category.CategoryReq;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryListAll")
    public ResponseEntity<List<CategoryRes>> getCategoryListAll(){
        List<CategoryRes> categoryResList= categoryService.getListAll();
        System.out.println(categoryResList.size());
        return ResponseEntity.ok(categoryResList);
    }

    @GetMapping("/getCategoryFindById/{id}")
    public ResponseEntity<CategoryRes> getCategoryFindById(@PathVariable("id") Long id){
        CategoryRes categoryRes = categoryService.getFindById(id);
        return ResponseEntity.ok(categoryRes);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryRes> addCategory(@RequestBody CategoryReq categoryReq){
        CategoryRes categoryRes = categoryService.addCategory(categoryReq);
        return ResponseEntity.ok(categoryRes);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<CategoryRes> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryReq categoryReq){
        CategoryRes categoryRes = categoryService.updateCategory(id, categoryReq);
        return ResponseEntity.ok(categoryRes);
    }

    @DeleteMapping("/deleteCategoryById/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long id){
        String rtn = categoryService.deleteCategory(id);
        return ResponseEntity.ok(rtn);
    }
}
