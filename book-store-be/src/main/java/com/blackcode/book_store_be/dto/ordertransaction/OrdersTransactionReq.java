package com.blackcode.book_store_be.dto.ordertransaction;

import com.blackcode.book_store_be.dto.ordersitems.OrdersItemsReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdersTransactionReq {

    private String user_id;

    private String orderTotalPrice;

    private String ordersStatus;

    private String ordersCreateAt;

    private ArrayList<OrdersItemsReq> ordersItemsReq;
}
