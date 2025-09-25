package com.blackcode.book_store_be.dto.ordersitems;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdersItemsRes {
    private String orderItemsId;
    private String order_id;
    private String books_id;
    private String quantity;
    private String unitPrice;
}
