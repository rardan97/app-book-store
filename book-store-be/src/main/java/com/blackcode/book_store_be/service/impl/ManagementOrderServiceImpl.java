package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.dto.managementorder.ManagementOrderRes;
import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import com.blackcode.book_store_be.model.Orders;
import com.blackcode.book_store_be.model.OrdersItems;
import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.repository.OrdersItemsRepository;
import com.blackcode.book_store_be.repository.OrdersRepository;
import com.blackcode.book_store_be.repository.UserRepository;
import com.blackcode.book_store_be.service.ManagementOrdersService;
import com.blackcode.book_store_be.service.ManagementUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagementOrderServiceImpl implements ManagementOrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersItemsRepository ordersItemsRepository;

    @Autowired
    private ManagementUserService managementUserService;

    @Override
    public List<ManagementOrderRes> getListAll() {
        List<Orders> ordersList = ordersRepository.findAll();
        List<ManagementOrderRes> orderListRes = new ArrayList<>();
        for (Orders rowOrder : ordersList) {
            System.out.println("Data User " + rowOrder.getOrdersId());
            ManagementUserRes managementUserRes = new ManagementUserRes();
            ManagementUserRes userManagement = managementUserService.getFindById(rowOrder.getUserOder().getUserId());
            if (userManagement != null) {
                ManagementOrderRes managementOrderRes = new ManagementOrderRes();
                managementOrderRes.setOrdersId(rowOrder.getOrdersId());
                managementOrderRes.setOrdersStatus(rowOrder.getOrdersStatus());
                managementOrderRes.setOrdersCreateAt(rowOrder.getOrdersCreateAt());
                managementOrderRes.setUserOrderRes(userManagement);
                orderListRes.add(managementOrderRes);
            }

            System.out.println("heck data :" + orderListRes.size());

        }
        return orderListRes;
    }

    @Override
    public ManagementOrderRes getFindById(Long orderId) {
        Optional<Orders> orders = ordersRepository.findById(orderId);
        if(orders.isPresent()){

            List<OrdersItems> ordersItemsList = ordersItemsRepository.findByOrder_OrdersId(orderId);
            for (OrdersItems rowOrdersItems : ordersItemsList){
                System.out.println("rowOrdersItems : "+rowOrdersItems.getOrdersItemsId());

            }


        }
        return null;
    }


}
