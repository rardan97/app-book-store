package com.blackcode.book_store_be.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DataItemsDetailsDto {
    private String id;
    private String price;
    private String quantity;
    private String name;
}
