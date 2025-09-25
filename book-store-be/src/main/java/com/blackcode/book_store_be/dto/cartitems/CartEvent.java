package com.blackcode.book_store_be.dto.cartitems;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartEvent {
    private Long userId;
    private Long bookId;
    private Integer quantity;
    private String action; // "add", "update", "remove", "clear"
}
