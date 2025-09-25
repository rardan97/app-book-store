package com.blackcode.book_store_be.dto.roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class StaffRoleRes {

    private Long roleStaffId;

    private String roleStaffName;
}
