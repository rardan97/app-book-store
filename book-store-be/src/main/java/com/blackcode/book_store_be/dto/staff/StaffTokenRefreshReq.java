package com.blackcode.book_store_be.dto.staff;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StaffTokenRefreshReq {
    @NotBlank
    private String refreshToken;
}
