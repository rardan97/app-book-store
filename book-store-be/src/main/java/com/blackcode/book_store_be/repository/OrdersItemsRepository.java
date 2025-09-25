package com.blackcode.book_store_be.repository;

import com.blackcode.book_store_be.model.Orders;
import com.blackcode.book_store_be.model.OrdersItems;
import com.blackcode.book_store_be.model.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersItemsRepository extends JpaRepository<OrdersItems, Long> {

    List<OrdersItems> findByOrder_OrdersId(Long orderId);
}
