package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.staff.*;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthStaffService {

    StaffJwtRes singIn(StaffLoginReq loginRequest);

    StaffMessageRes signUp(StaffSignUpReq staffSignUpReq);

    StaffTokenRefreshRes refreshToken(StaffTokenRefreshReq request);

    StaffMessageRes signOut(HttpServletRequest request);

}
