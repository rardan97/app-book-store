package com.blackcode.book_store_be.controller.user_controller;

import com.blackcode.book_store_be.dto.ordersitems.OrdersItemsReq;
import com.blackcode.book_store_be.dto.ordersitems.OrdersItemsRes;
import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionReq;
import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionRes;
import com.blackcode.book_store_be.dto.payment.CheckoutTransactionRes;
import com.blackcode.book_store_be.dto.payment.TransaksiPembelianRequest;
import com.blackcode.book_store_be.service.BooksService;
import com.blackcode.book_store_be.service.MidtransService;
import com.blackcode.book_store_be.service.OrdersTransactionService;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/orderTransaction")
public class OrderTransactionController {

    @Autowired
    private OrdersTransactionService ordersTransactionService;

    @Autowired
    private MidtransService midtransService;



    private final OkHttpClient client = new OkHttpClient();

    @PostMapping("/addOrderTransaction")
    public ResponseEntity<OrdersTransactionRes> addOrderTransaction(@RequestBody OrdersTransactionReq ordersTransactionReq){
        OrdersTransactionRes ordersTransactionRes = ordersTransactionService.addOrdersTransaction(ordersTransactionReq);
        return ResponseEntity.ok(ordersTransactionRes);
    }

    @PostMapping("/checkoutTransaction")
    public ResponseEntity<CheckoutTransactionRes> createTransactionToken(@RequestBody TransaksiPembelianRequest transaksiPembelianRequest) {
        try {
            String transaksiKode = transaksiPembelianRequest.getDataProductTransaksi().getTransaksiKode();
            String transaksiTotal = transaksiPembelianRequest.getDataProductTransaksi().getTransaksiTotal();

            // Log the incoming data for debugging
            System.out.println("Transaksi Kode: " + transaksiKode);
            System.out.println("Transaksi Total: " + transaksiTotal);
            CheckoutTransactionRes data = midtransService.createTransactionToken(transaksiPembelianRequest);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Proccess Checkout");
        }
    }

}
