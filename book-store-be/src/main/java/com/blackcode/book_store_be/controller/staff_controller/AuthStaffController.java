package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.roles.StaffRoleRes;
import com.blackcode.book_store_be.dto.staff.*;
import com.blackcode.book_store_be.service.AuthStaffService;
import com.blackcode.book_store_be.service.StaffRoleService;
import com.blackcode.book_store_be.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/staff")
public class AuthStaffController {

    private final AuthStaffService authStaffService;

    private final StaffRoleService staffRoleService;

    public AuthStaffController(AuthStaffService authStaffService, StaffRoleService staffRoleService) {
        this.authStaffService = authStaffService;
        this.staffRoleService = staffRoleService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<?>> authenticateStaff(@Valid @RequestBody StaffLoginReq loginRequest){
        StaffJwtRes rtn = authStaffService.singIn(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("Login Success", 200, rtn));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> registerStaff(@Valid @RequestBody StaffSignUpReq staffSignUpReq){
        StaffMessageRes petugasMessageRes = authStaffService.signUp(staffSignUpReq);
        return ResponseEntity.ok(ApiResponse.success("Success Registration", 200, petugasMessageRes));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<ApiResponse<?>> refreshtoken(@Valid @RequestBody StaffTokenRefreshReq request){
        StaffTokenRefreshRes petugasTokenRefreshRes = authStaffService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", 200, petugasTokenRefreshRes));
    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<?>> logout(HttpServletRequest request) {
        StaffMessageRes petugasMessageRes = authStaffService.signOut(request);
        return ResponseEntity.ok(ApiResponse.success("Logout successful", 200, petugasMessageRes));
    }

    @GetMapping("/getRoleListAll")
    public ResponseEntity<ApiResponse<List<StaffRoleRes>>> getRoleListAll(){
        List<StaffRoleRes> staffRoleListRes = staffRoleService.getListAllRole();
        return ResponseEntity.ok(ApiResponse.success("Role retrieved successfully", 200, staffRoleListRes));
    }

}