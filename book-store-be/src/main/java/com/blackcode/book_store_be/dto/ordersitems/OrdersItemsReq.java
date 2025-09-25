package com.blackcode.book_store_be.dto.ordersitems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdersItemsReq {
    private String booksId;
    private String quantity;
    private String unitPrice;
}