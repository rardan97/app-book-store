package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.category.CategoryReq;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.model.Category;
import com.blackcode.book_store_be.repository.CategoryRepository;
import com.blackcode.book_store_be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryRes> getListAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryRes> categoryResList = new ArrayList<>();
        for (Category rowCategory : categories){
            System.out.println("Data Category "+rowCategory.getCategoryId());

            CategoryRes categoryRes = new CategoryRes();
            categoryRes.setCategoryId(rowCategory.getCategoryId());
            categoryRes.setCategoryName(rowCategory.getCategoryName());
            categoryRes.setCategoryDescription(rowCategory.getCategoryDescription());
            categoryResList.add(categoryRes);

        }

        System.out.println("heck data :"+categoryResList.size());
        return categoryResList;
    }

    @Override
    public CategoryRes getFindById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        CategoryRes categoryRes = null;
        if(category.isPresent()){
            categoryRes = new CategoryRes();
            categoryRes.setCategoryId(category.get().getCategoryId());
            categoryRes.setCategoryName(category.get().getCategoryName());
            categoryRes.setCategoryDescription(category.get().getCategoryDescription());
            return categoryRes;
        }
        return null;
    }

    @Override
    public CategoryRes addCategory(CategoryReq categoryReq) {
        CategoryRes categoryRes = new CategoryRes();
        Category categoryx = new Category();
        categoryx.setCategoryName(categoryReq.getCategoryName());
        categoryx.setCategoryDescription(categoryReq.getCategoryDescription());
        Category category1 = categoryRepository.save(categoryx);
        categoryRes.setCategoryId(category1.getCategoryId());
        categoryRes.setCategoryName(category1.getCategoryName());
        categoryRes.setCategoryDescription(category1.getCategoryDescription());
        return categoryRes;
    }

    @Override
    public CategoryRes updateCategory(Long categoryId, CategoryReq categoryReq) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            CategoryRes categoryRes = new CategoryRes();
            category.get().setCategoryName(categoryReq.getCategoryName());
            category.get().setCategoryDescription(categoryReq.getCategoryDescription());
            Category category1 = categoryRepository.save(category.get());
            categoryRes.setCategoryId(category1.getCategoryId());
            categoryRes.setCategoryName(category1.getCategoryName());
            categoryRes.setCategoryDescription(category1.getCategoryDescription());
            return categoryRes;
        }

        return null;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        System.out.println("check id :"+categoryId);
        if(category.isPresent()){
            System.out.println("Proccess Delete Data Id : "+categoryId);
            categoryRepository.deleteById(categoryId);
            return "success";
        }
        return null;
    }

}
