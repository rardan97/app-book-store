package com.blackcode.book_store_be.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataProductTransaksi {
    private Integer transaksiId;

    private String transaksiKode;

    private String transaksiTotal;
}
