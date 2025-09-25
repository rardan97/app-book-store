package com.blackcode.book_store_be.dto.payment;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutTransactionRes {

    private CustomerDetailDto customerDetails;
    private List<DataItemsDetailsDto> itemDetails;
    private DataTotalTransactionDto dataTotalTransaction;
    private String token;

}
