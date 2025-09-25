package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionReq;
import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionRes;
import org.springframework.stereotype.Service;

@Service
public interface OrdersTransactionService {

    OrdersTransactionRes addOrdersTransaction(OrdersTransactionReq ordersTransactionReq);


}
