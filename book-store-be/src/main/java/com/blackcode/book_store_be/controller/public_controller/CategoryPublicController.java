package com.blackcode.book_store_be.controller.public_controller;


import com.blackcode.book_store_be.dto.books.BooksRes;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/category")
public class CategoryPublicController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryListAll")
    public ResponseEntity<List<CategoryRes>> getBooksListAll(){
        return ResponseEntity.ok(categoryService.getListAll());
    }

    @GetMapping("/getCategoryFindById/{id}")
    public ResponseEntity<CategoryRes> getBooksFindById(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getFindById(id));
    }
}
