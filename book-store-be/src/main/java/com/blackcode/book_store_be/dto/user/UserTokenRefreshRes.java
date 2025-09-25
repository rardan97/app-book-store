package com.blackcode.book_store_be.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenRefreshRes {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public UserTokenRefreshRes(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
