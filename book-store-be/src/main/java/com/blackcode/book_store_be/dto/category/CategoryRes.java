package com.blackcode.book_store_be.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRes {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
}
