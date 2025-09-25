package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.roles.StaffRoleReq;
import com.blackcode.book_store_be.dto.roles.StaffRoleRes;
import com.blackcode.book_store_be.service.StaffRoleService;
import com.blackcode.book_store_be.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/role")

public class RoleController {

    private final StaffRoleService staffRoleService;

    public RoleController(StaffRoleService staffRoleService) {
        this.staffRoleService = staffRoleService;
    }

    @GetMapping("/getRoleListAll")
    public ResponseEntity<ApiResponse<List<StaffRoleRes>>> getRoleListAll(){
        List<StaffRoleRes> staffRoleListRes = staffRoleService.getListAllRole();
        return ResponseEntity.ok(ApiResponse.success("Staff Role retrieved successfully", 200, staffRoleListRes));
    }

    @GetMapping("/getRoleFindById/{id}")
    public ResponseEntity<ApiResponse<StaffRoleRes>> getRoleFindById(@PathVariable("id") Long id){
        StaffRoleRes staffRoleRes = staffRoleService.getFindRoleById(id);
        return ResponseEntity.ok(ApiResponse.success("Staff Role found", 200, staffRoleRes));
    }

    @PostMapping("/addRole")
    public ResponseEntity<ApiResponse<StaffRoleRes>> addRole(@RequestBody StaffRoleReq staffRoleReq){
        StaffRoleRes staffRoleRes = staffRoleService.addRole(staffRoleReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("staff Role created", 201, staffRoleRes));
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<ApiResponse<StaffRoleRes>> updateRole(@PathVariable("id") Long id, @RequestBody StaffRoleReq staffRoleReq){
        StaffRoleRes staffRoleRes = staffRoleService.updateRole(id, staffRoleReq);
        return ResponseEntity.ok(ApiResponse.success("staff Role Update", 200, staffRoleRes));
    }

    @DeleteMapping("/deleteRoleById/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRoleById(@PathVariable("id") Long id){
        String rtn = staffRoleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success("staff Role success Delete", 200, rtn));
    }
}
