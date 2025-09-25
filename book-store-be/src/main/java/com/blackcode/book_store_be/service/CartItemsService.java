package com.blackcode.book_store_be.service;

import com.blackcode.book_store_be.dto.cartitems.CartItemsReq;
import com.blackcode.book_store_be.dto.cartitems.CartItemsRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemsService {

    void testRedis();

    List<CartItemsRes> getCart(Long userId);



    void addToCart(Long userId, CartItemsReq cart);

    void removeFromCart(Long userId, Long bookId);

    void clearCart(Long userId);

    void updateQuantity(Long userId, Long bookId, Integer quantity);


}
