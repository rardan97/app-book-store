package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.dto.managementorder.ManagementOrderRes;
import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagementOrdersService {

    List<ManagementOrderRes> getListAll();

    ManagementOrderRes getFindById(Long orderId);



}
