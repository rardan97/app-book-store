package com.blackcode.book_store_be.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataProductPembelian {
    private Integer productId;
    private String productNama;
    private String productHarga;
    private String productQty;
    private String productTotalHarga;
}
