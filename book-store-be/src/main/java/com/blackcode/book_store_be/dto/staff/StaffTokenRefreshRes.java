package com.blackcode.book_store_be.dto.staff;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffTokenRefreshRes {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public StaffTokenRefreshRes(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
