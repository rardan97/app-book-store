package com.blackcode.book_store_be.dto.staff;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StaffJwtRes {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long staffId;
    private String staffUserName;
    private List<String> staffRoles;

    public StaffJwtRes(String token, String refreshToken, Long staffId, String staffUserName, List<String> staffRoles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.staffId = staffId;
        this.staffUserName = staffUserName;
        this.staffRoles = staffRoles;
    }
}
