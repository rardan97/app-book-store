package com.blackcode.book_store_be.dto.managementorder;

import com.blackcode.book_store_be.dto.managementuser.ManagementUserRes;
import com.blackcode.book_store_be.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagementOrderRes {

    private Long ordersId;

    private ManagementUserRes userOrderRes;

    private String orderTotalPrice;

    private String ordersStatus;

    private String ordersCreateAt;
}
