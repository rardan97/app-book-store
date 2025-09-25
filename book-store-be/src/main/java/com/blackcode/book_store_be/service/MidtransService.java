package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.payment.CheckoutTransactionRes;
import com.blackcode.book_store_be.dto.payment.TransaksiPembelianRequest;
import com.midtrans.httpclient.error.MidtransError;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface MidtransService {

    CheckoutTransactionRes createTransactionToken(TransaksiPembelianRequest transaksiPembelianRequest) throws MidtransError;
}
