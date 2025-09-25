package com.blackcode.book_store_be.controller.user_controller;

import com.blackcode.book_store_be.dto.user.*;
import com.blackcode.book_store_be.service.AuthUserService;
import com.blackcode.book_store_be.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/user")
public class AuthUserController {

    private final AuthUserService authUserService;

    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<?>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse rtn = authUserService.singIn(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("Login Success", 200, rtn));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody UserSignUpReq signupRequest){
        MessageRes petugasMessageRes = authUserService.signUp(signupRequest);
        return ResponseEntity.ok(ApiResponse.success("Success Registration", 200, petugasMessageRes));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<ApiResponse<?>> refreshtoken(@Valid @RequestBody UserTokenRefreshReq request){
        UserTokenRefreshRes tokenRefreshRes = authUserService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", 200, tokenRefreshRes));
    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<?>> logout(HttpServletRequest request) {
        MessageRes petugasMessageRes = authUserService.signOut(request);
        return ResponseEntity.ok(ApiResponse.success("Logout successful", 200, petugasMessageRes));
    }

}