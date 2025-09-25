package com.blackcode.book_store_be.controller.user_controller;


import com.blackcode.book_store_be.dto.cartitems.CartItemsReq;
import com.blackcode.book_store_be.dto.cartitems.CartItemsRes;
import com.blackcode.book_store_be.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user/cartTransaction")
public class CartTransactionController {

    @Autowired
    private CartItemsService cartItemsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    @GetMapping("/getCart/{userId}")
    public List<CartItemsRes> getCart(@PathVariable("userId") Long userId) {
        System.out.println("check ID "+userId);
        return cartItemsService.getCart(userId);
    }

    @PostMapping("/addCart/{userId}")
    public void addToCart(@PathVariable("userId") Long userId, @RequestBody CartItemsReq item) {
        cartItemsService.addToCart(userId, item);
    }

    @DeleteMapping("/removeCart/{userId}/{bookId}")
    public void removeFromCart(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId) {
        cartItemsService.removeFromCart(userId, bookId);
    }

    @PostMapping("/clearCart/{userId}")
    public void clearCart(@PathVariable("userId") Long userId) {
        cartItemsService.clearCart(userId);
    }

    @PostMapping("/updateCartQty/{userId}/{bookId}/{quantity}")
    public void updateCartQty(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId, @PathVariable("quantity") Long quantity) {
        cartItemsService.updateQuantity(userId, bookId, quantity.intValue());
    }

    @GetMapping("/testRedis")
    public void testRedis() {
        cartItemsService.testRedis();
    }

    @GetMapping("/redis-set-test")
    public String redisSetTest() {
        try {
            redisTemplate.opsForValue().set("test-key", "hello-redis");
            return "Key set successfully";
        } catch (Exception e) {
            return "Failed to set key: " + e.getMessage();
        }
    }

    @GetMapping("/redis-get-test")
    public String redisGetTest() {
        try {
            Object val = redisTemplate.opsForValue().get("test-key");
            return "Value for test-key: " + val;
        } catch (Exception e) {
            return "Failed to get key: " + e.getMessage();
        }
    }

    @GetMapping("/redis-keys")
    public Set<String> getKeys() {
        return redisTemplate.keys("*");
    }





}
