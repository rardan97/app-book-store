package com.blackcode.book_store_be.dto.staff;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffSignUpReq {

    @NotBlank
    @Size(min = 3, max = 20)
    private String staffFullName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String staffUsername;

    @NotBlank
    @Size(min = 3, max = 40)
    private String staffPassword;

    @NotBlank
    private String staffEmail;

    private String role;
}
