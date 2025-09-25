package com.blackcode.book_store_be.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long userId;
    private String userName;

    public JwtResponse(String token, String refreshToken, Long userId, String userName) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.userName = userName;
    }
}
