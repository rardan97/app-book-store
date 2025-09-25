package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.user.*;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthUserService {

    JwtResponse singIn(LoginRequest loginRequest);

    MessageRes signUp(UserSignUpReq userSignUpReq);

    UserTokenRefreshRes refreshToken(UserTokenRefreshReq request);

    MessageRes signOut(HttpServletRequest request);

}
