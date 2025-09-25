package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.category.CategoryReq;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<CategoryRes> getListAll();

    CategoryRes getFindById(Long categoryId);

    CategoryRes addCategory(CategoryReq category);

    CategoryRes updateCategory(Long categoryId, CategoryReq category);

    String deleteCategory(Long categoryId);
}
