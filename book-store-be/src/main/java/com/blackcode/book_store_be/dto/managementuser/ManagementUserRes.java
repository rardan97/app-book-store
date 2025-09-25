package com.blackcode.book_store_be.dto.managementuser;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagementUserRes {

    private Long userId;
    private String userFullName;
    private String userName;
    private String userEmail;
    private String userPassword;
}
