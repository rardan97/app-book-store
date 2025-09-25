package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.category.CategoryReq;
import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.dto.roles.StaffRoleReq;
import com.blackcode.book_store_be.dto.roles.StaffRoleRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffRoleService {

    List<StaffRoleRes> getListAllRole();

    StaffRoleRes getFindRoleById(Long roleId);

    StaffRoleRes addRole(StaffRoleReq staffRoleReq);

    StaffRoleRes updateRole(Long roleId, StaffRoleReq staffRoleReq);

    String deleteRole(Long roleId);

}
