package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import com.blackcode.book_store_be.service.ManagementUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/staff/orderManagement")
public class ManagementOrdersController {

    @Autowired
    private ManagementUserService userManagementService;

    @GetMapping("/getOrderManagementListAll")
    public ResponseEntity<List<ManagementUserRes>> getOrderManagementListAll(){
        List<ManagementUserRes> userManagementResList= userManagementService.getListAll();
        System.out.println(userManagementResList.size());
        return ResponseEntity.ok(userManagementResList);
    }

    @GetMapping("/getOrderManagementFindById/{id}")
    public ResponseEntity<ManagementUserRes> getOrderManagementFindById(@PathVariable("id") Long id){
        ManagementUserRes userManagementRes = userManagementService.getFindById(id);
        return ResponseEntity.ok(userManagementRes);
    }
}
