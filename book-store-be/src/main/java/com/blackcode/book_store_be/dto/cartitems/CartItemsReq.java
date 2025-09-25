package com.blackcode.book_store_be.dto.cartitems;

import com.blackcode.book_store_be.model.Books;
import com.blackcode.book_store_be.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemsReq {

    private Long bookId;

    private String bookTitle;

    private String price;

    private String bookImage;

    private int quantity;
}
