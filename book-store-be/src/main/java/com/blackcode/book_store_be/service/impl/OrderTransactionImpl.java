package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.books.BooksRes;
import com.blackcode.book_store_be.dto.ordersitems.OrdersItemsReq;
import com.blackcode.book_store_be.dto.ordersitems.OrdersItemsRes;
import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionReq;
import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionRes;
import com.blackcode.book_store_be.model.Books;
import com.blackcode.book_store_be.model.Orders;
import com.blackcode.book_store_be.model.OrdersItems;
import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.repository.BooksRepository;
import com.blackcode.book_store_be.repository.OrdersItemsRepository;
import com.blackcode.book_store_be.repository.OrdersRepository;
import com.blackcode.book_store_be.repository.UserRepository;
import com.blackcode.book_store_be.service.BooksService;
import com.blackcode.book_store_be.service.OrdersTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderTransactionImpl implements OrdersTransactionService {

    @Autowired
    private OrdersItemsRepository ordersItemsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Override
    public OrdersTransactionRes addOrdersTransaction(OrdersTransactionReq ordersTransactionReq) {

        ArrayList<OrdersItemsRes> ordersItemsRes = new ArrayList<>();
        OrdersTransactionRes ordersTransactionRes = new OrdersTransactionRes();
        List<OrdersItemsReq> ordersItemsReq = ordersTransactionReq.getOrdersItemsReq();


        Orders orders = new Orders();

        Optional<User> user = userRepository.findById(Long.valueOf(ordersTransactionReq.getUser_id()));
        orders.setUserOder(user.get());
        orders.setOrdersStatus(ordersTransactionReq.getOrdersStatus());
        orders.setOrderTotalPrice(ordersTransactionReq.getOrderTotalPrice());
        orders.setOrdersCreateAt(ordersTransactionReq.getOrdersCreateAt());

        Orders orders1 = ordersRepository.save(orders);

        ordersTransactionRes.setOrdersId(orders1.getOrdersId());
        ordersTransactionRes.setOrderTotalPrice(orders1.getOrderTotalPrice());
        ordersTransactionRes.setOrdersStatus(orders1.getOrdersStatus());
        ordersTransactionRes.setUser_id(orders1.getUserOder().getUserId().toString());
        ordersTransactionRes.setOrdersCreateAt(orders1.getOrdersCreateAt());

        for (OrdersItemsReq rowOrderItems : ordersItemsReq){
            OrdersItems ordersItem = new OrdersItems();
            ordersItem.setOrder(orders1);
            ordersItem.setQuantity(Integer.parseInt(rowOrderItems.getQuantity()));
            Optional<Books> books = booksRepository.findById(Long.valueOf(rowOrderItems.getBooksId()));
            ordersItem.setBooks(books.get());
            ordersItem.setUnit_price(rowOrderItems.getUnitPrice());
            OrdersItems ordersItemSave = ordersItemsRepository.save(ordersItem);

            OrdersItemsRes ordersItemsRes1 = new OrdersItemsRes();

            ordersItemsRes1.setOrderItemsId(ordersItemSave.getOrdersItemsId().toString());
            ordersItemsRes1.setOrder_id(ordersItemSave.getOrder().getOrdersId().toString());
            ordersItemsRes1.setBooks_id(ordersItemSave.getBooks().getBookId().toString());
            ordersItemsRes1.setUnitPrice(ordersItemSave.getUnit_price());
            ordersItemsRes1.setQuantity(ordersItemSave.getQuantity().toString());
            ordersItemsRes.add(ordersItemsRes1);
        }
        ordersTransactionRes.setOrdersItemsReq(ordersItemsRes);
        return ordersTransactionRes;
    }
}
