package com.blackcode.book_store_be.dto.cartitems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemsRes {

    private Long bookId;

    private String bookTitle;

    private String price;

    private String bookImage;

    private int quantity;
}
