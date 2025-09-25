package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import com.blackcode.book_store_be.service.ManagementUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/userManagement")
public class ManagementUserController {

    private final ManagementUserService managementUserService;

    public ManagementUserController(ManagementUserService managementUserService) {
        this.managementUserService = managementUserService;
    }

    @GetMapping("/getUserManagementListAll")
    public ResponseEntity<List<ManagementUserRes>> getUserManagementListAll(){
        List<ManagementUserRes> userManagementResList= managementUserService.getListAll();
        System.out.println(userManagementResList.size());
        return ResponseEntity.ok(userManagementResList);
    }

    @GetMapping("/getUserManagementFindById/{id}")
    public ResponseEntity<ManagementUserRes> getUserManagementFindById(@PathVariable("id") Long id){
        ManagementUserRes userManagementRes = managementUserService.getFindById(id);
        return ResponseEntity.ok(userManagementRes);
    }
}
