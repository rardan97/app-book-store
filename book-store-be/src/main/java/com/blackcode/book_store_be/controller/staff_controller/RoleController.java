package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.roles.StaffRoleReq;
import com.blackcode.book_store_be.dto.roles.StaffRoleRes;
import com.blackcode.book_store_be.service.StaffRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/staff/role")

public class RoleController {

    @Autowired
    private StaffRoleService staffRoleService;

    @GetMapping("/getRoleListAll")
    public ResponseEntity<List<StaffRoleRes>> getRoleListAll(){
        List<StaffRoleRes> staffRoleListRes = staffRoleService.getListAllRole();
        System.out.println(staffRoleListRes.size());
        return ResponseEntity.ok(staffRoleListRes);
    }

    @GetMapping("/getRoleFindById/{id}")
    public ResponseEntity<StaffRoleRes> getRoleFindById(@PathVariable("id") Long id){
        StaffRoleRes staffRoleRes = staffRoleService.getFindRoleById(id);
        return ResponseEntity.ok(staffRoleRes);
    }

    @PostMapping("/addRole")
    public ResponseEntity<StaffRoleRes> addRole(@RequestBody StaffRoleReq staffRoleReq){
        StaffRoleRes staffRoleRes = staffRoleService.addRole(staffRoleReq);
        return ResponseEntity.ok(staffRoleRes);
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<StaffRoleRes> updateRole(@PathVariable("id") Long id, @RequestBody StaffRoleReq staffRoleReq){
        StaffRoleRes staffRoleRes = staffRoleService.updateRole(id, staffRoleReq);
        return ResponseEntity.ok(staffRoleRes);
    }

    @DeleteMapping("/deleteRoleById/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable("id") Long id){
        String rtn = staffRoleService.deleteRole(id);
        return ResponseEntity.ok(rtn);
    }
}
