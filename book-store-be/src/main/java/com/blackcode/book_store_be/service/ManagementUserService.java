package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagementUserService {

    List<ManagementUserRes> getListAll();

    ManagementUserRes getFindById(Long userId);
}
