package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.category.CategoryRes;
import com.blackcode.book_store_be.dto.roles.StaffRoleReq;
import com.blackcode.book_store_be.dto.roles.StaffRoleRes;
import com.blackcode.book_store_be.model.Category;
import com.blackcode.book_store_be.model.staff.StaffRole;
import com.blackcode.book_store_be.repository.StaffRoleRepository;
import com.blackcode.book_store_be.service.StaffRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffRoleServiceImpl implements StaffRoleService {


    @Autowired
    private StaffRoleRepository staffRoleRepository;


    @Override
    public List<StaffRoleRes> getListAllRole() {
        List<StaffRole> staffRoles = staffRoleRepository.findAll();
        List<StaffRoleRes> staffRoleResList = new ArrayList<>();
        for (StaffRole rowStaffrole : staffRoles){
            System.out.println("Data Category "+rowStaffrole.getRoleStaffId());
            StaffRoleRes staffRoleRes = new StaffRoleRes();
            staffRoleRes.setRoleStaffId(rowStaffrole.getRoleStaffId());
            staffRoleRes.setRoleStaffName(rowStaffrole.getRoleStaffName());
            staffRoleResList.add(staffRoleRes);
        }
        System.out.println("heck data :"+staffRoleResList.size());
        return staffRoleResList;
    }

    @Override
    public StaffRoleRes getFindRoleById(Long roleId) {
        Optional<StaffRole> staffRole = staffRoleRepository.findById(roleId);
        StaffRoleRes staffRoleRes = null;
        if(staffRole.isPresent()){
            staffRoleRes = new StaffRoleRes();
            staffRoleRes.setRoleStaffId(staffRole.get().getRoleStaffId());
            staffRoleRes.setRoleStaffName(staffRole.get().getRoleStaffName());
            return staffRoleRes;
        }
        return null;
    }

    @Override
    public StaffRoleRes addRole(StaffRoleReq staffRoleReq) {
        StaffRoleRes staffRoleRes = new StaffRoleRes();
        StaffRole staffRole = new StaffRole();
        staffRole.setRoleStaffName(staffRoleReq.getRoleStaffName());
        StaffRole staffRole1 = staffRoleRepository.save(staffRole);

        staffRoleRes.setRoleStaffId(staffRole1.getRoleStaffId());
        staffRoleRes.setRoleStaffName(staffRole1.getRoleStaffName());

        return staffRoleRes;
    }

    @Override
    public StaffRoleRes updateRole(Long roleId, StaffRoleReq staffRoleReq) {
        Optional<StaffRole> staffRole = staffRoleRepository.findById(roleId);
        if(staffRole.isPresent()){
            StaffRoleRes staffRoleRes = new StaffRoleRes();
            staffRole.get().setRoleStaffName(staffRoleReq.getRoleStaffName());
            StaffRole staffRole1 = staffRoleRepository.save(staffRole.get());
            staffRoleRes.setRoleStaffId(staffRole1.getRoleStaffId());
            staffRoleRes.setRoleStaffName(staffRole1.getRoleStaffName());
            return staffRoleRes;
        }
        return null;
    }

    @Override
    public String deleteRole(Long roleId) {
        Optional<StaffRole> staffRole = staffRoleRepository.findById(roleId);
        System.out.println("check id :"+roleId);
        if(staffRole.isPresent()){
            System.out.println("Process Delete Data Id : "+roleId);
            staffRoleRepository.deleteById(roleId);
            return "success";
        }
        return null;
    }
}
