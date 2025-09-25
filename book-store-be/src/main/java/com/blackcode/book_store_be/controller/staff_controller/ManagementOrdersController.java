package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import com.blackcode.book_store_be.service.ManagementUserService;
import com.blackcode.book_store_be.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staff/orderManagement")
public class ManagementOrdersController {

    private final ManagementUserService userManagementService;

    public ManagementOrdersController(ManagementUserService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/getOrderManagementListAll")
    public ResponseEntity<ApiResponse<List<ManagementUserRes>>> getOrderManagementListAll(){
        List<ManagementUserRes> userManagementResList = userManagementService.getListAll();
        return ResponseEntity.ok(ApiResponse.success("Order Management retrieved successfully", 200, userManagementResList));
    }

    @GetMapping("/getOrderManagementFindById/{id}")
    public ResponseEntity<ApiResponse<ManagementUserRes>> getOrderManagementFindById(@PathVariable("id") Long id){
        ManagementUserRes userManagementRes = userManagementService.getFindById(id);
        return ResponseEntity.ok(ApiResponse.success("Order Management found", 200, userManagementRes));
    }
}
