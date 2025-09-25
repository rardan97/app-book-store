package com.blackcode.book_store_be.dto.staff;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StaffLoginReq {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
