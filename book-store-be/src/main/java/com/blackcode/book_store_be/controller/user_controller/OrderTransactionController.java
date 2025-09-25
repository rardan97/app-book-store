package com.blackcode.book_store_be.controller.user_controller;

import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionReq;
import com.blackcode.book_store_be.dto.ordertransaction.OrdersTransactionRes;
import com.blackcode.book_store_be.dto.payment.CheckoutTransactionRes;
import com.blackcode.book_store_be.dto.payment.TransaksiPembelianRequest;
import com.blackcode.book_store_be.service.MidtransService;
import com.blackcode.book_store_be.service.OrdersTransactionService;
import com.blackcode.book_store_be.utils.ApiResponse;
import okhttp3.OkHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/orderTransaction")
public class OrderTransactionController {

    private final OkHttpClient client = new OkHttpClient();

    private final OrdersTransactionService ordersTransactionService;

    private final MidtransService midtransService;

    public OrderTransactionController(OrdersTransactionService ordersTransactionService, MidtransService midtransService) {
        this.ordersTransactionService = ordersTransactionService;
        this.midtransService = midtransService;
    }

    @PostMapping("/addOrderTransaction")
    public ResponseEntity<ApiResponse<OrdersTransactionRes>> addOrderTransaction(@RequestBody OrdersTransactionReq ordersTransactionReq){
        OrdersTransactionRes ordersTransactionRes = ordersTransactionService.addOrdersTransaction(ordersTransactionReq);
        return ResponseEntity.ok(ApiResponse.success("Orders Transaction retrieved successfully", 200, ordersTransactionRes));
    }

    @PostMapping("/checkoutTransaction")
    public ResponseEntity<ApiResponse<CheckoutTransactionRes>> createTransactionToken(@RequestBody TransaksiPembelianRequest transaksiPembelianRequest) {
        try {
            String transaksiKode = transaksiPembelianRequest.getDataProductTransaksi().getTransaksiKode();
            String transaksiTotal = transaksiPembelianRequest.getDataProductTransaksi().getTransaksiTotal();
            CheckoutTransactionRes data = midtransService.createTransactionToken(transaksiPembelianRequest);
            return ResponseEntity.ok(ApiResponse.success("Category created successfully", 200, data));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Proccess Checkout");
        }
    }
}
