package com.blackcode.book_store_be.dto.books;

import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BooksRes {
    private Long bookId;

    private String bookTitle;

    private String author;

    private String description;

    private String price;

    private Integer stock;

    private String bookImage;

    private CategoryRes category;
}
