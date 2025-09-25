package com.blackcode.book_store_be.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenRefreshReq {
    @NotBlank
    private String refreshToken;
}
