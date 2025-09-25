package com.blackcode.book_store_be.dto.ordertransaction;

import com.blackcode.book_store_be.dto.ordersitems.OrdersItemsRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdersTransactionRes {
    private Long ordersId;

    private String user_id;

    private String orderTotalPrice;

    private String ordersStatus;

    private String ordersCreateAt;

    private ArrayList<OrdersItemsRes> ordersItemsReq;
}
