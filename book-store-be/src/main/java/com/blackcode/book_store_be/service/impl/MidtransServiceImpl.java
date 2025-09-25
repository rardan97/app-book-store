package com.blackcode.book_store_be.service.impl;


import com.blackcode.book_store_be.dto.payment.*;
import com.blackcode.book_store_be.service.MidtransService;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MidtransServiceImpl implements MidtransService {


    // Ini Masih Bermasalah
    @Value("${spring.kafka.bootstrap-servers}")
    private String server_key;

    private static final boolean IS_PRODUCTION = false;

    @Override
    public CheckoutTransactionRes createTransactionToken(TransaksiPembelianRequest transaksiPembelianRequest) throws MidtransError {


        CheckoutTransactionRes checkoutTransactionRes = new CheckoutTransactionRes();
        CustomerDetailDto customerDetailDto = new CustomerDetailDto();

        Midtrans.serverKey = server_key;  // Ini Masih Bermasalah kenapa ambile kafka server?
        Midtrans.isProduction = IS_PRODUCTION;

        UUID idRand = UUID.randomUUID();

        // Create parameters for the transaction
        Map<String, Object> params = new HashMap<>();

        Map<String, Object> transactionTotal = getStringObjectMap(transaksiPembelianRequest, idRand);
        List<DataItemsDetailsDto> dataItemsDetailsDto = getMidtransItemDetails(transaksiPembelianRequest);

        Map<String, String> customerDetails = new HashMap<>();
        customerDetails.put("first_name", "test");
        customerDetails.put("last_name", "claude");

        Map<String, String> creditCard = new HashMap<>();
        creditCard.put("secure", "true");
        params.put("transaction_details", transactionTotal);
        params.put("customer_details", customerDetails);
        params.put("item_details", dataItemsDetailsDto);
        params.put("credit_card", creditCard);

        // Create the transaction token by calling the SnapApi

        String tokenRtn = getDataToken(params);



        customerDetailDto.setFirst_name(customerDetails.get("first_name"));
        customerDetailDto.setLast_name(customerDetails.get("last_name"));

        String orderId = (String) transactionTotal.get("order_id");
        String gross_amount = (String) transactionTotal.get("gross_amount");

        DataTotalTransactionDto dataTotalTransactionDto = new DataTotalTransactionDto();
        dataTotalTransactionDto.setOrder_id(orderId);
        dataTotalTransactionDto.setTotal_amount(gross_amount);

        checkoutTransactionRes.setToken(tokenRtn);
        checkoutTransactionRes.setItemDetails(dataItemsDetailsDto);


        checkoutTransactionRes.setDataTotalTransaction(dataTotalTransactionDto);
        checkoutTransactionRes.setCustomerDetails(customerDetailDto);

        return checkoutTransactionRes;
    }

    @NotNull
    private static Map<String, Object> getStringObjectMap(TransaksiPembelianRequest transaksiPembelianRequest, UUID idRand) {

        System.out.println("grous ammount : "+transaksiPembelianRequest.getDataProductTransaksi().getTransaksiTotal());
        Map<String, Object> params = new HashMap<>();

        params.put("order_id", idRand.toString());
        params.put("gross_amount", String.valueOf(transaksiPembelianRequest.getDataProductTransaksi().getTransaksiTotal())); // Example amount in the smallest currency unit

        return params;
    }

    @NotNull
    private static List<DataItemsDetailsDto> getMidtransItemDetails(TransaksiPembelianRequest transaksiPembelianRequest) {
        List<DataItemsDetailsDto> dataRtn = new ArrayList<>();

        for (DataProductPembelian dataProductPembelian : transaksiPembelianRequest.getDataProductPembelian()) {
            DataItemsDetailsDto itemDtl = new DataItemsDetailsDto();
            itemDtl.setId(String.valueOf(dataProductPembelian.getProductId()));
            itemDtl.setPrice(dataProductPembelian.getProductHarga());
            itemDtl.setQuantity(dataProductPembelian.getProductQty());
            itemDtl.setName(dataProductPembelian.getProductNama());
            dataRtn.add(itemDtl);
        }
        return dataRtn;
    }


    private static String getDataToken(Map<String, Object> params) throws MidtransError {

        return SnapApi.createTransactionToken(params);

    }
}
