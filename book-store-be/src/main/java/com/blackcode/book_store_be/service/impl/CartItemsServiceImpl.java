package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.cartitems.CartEvent;
import com.blackcode.book_store_be.dto.cartitems.CartItemsReq;
import com.blackcode.book_store_be.dto.cartitems.CartItemsRes;
import com.blackcode.book_store_be.service.CartItemsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartItemsServiceImpl implements CartItemsService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    private String buildKey(Long userId) {
        return "cart:user:" + userId;
    }

    private static final Logger logger = LoggerFactory.getLogger(CartItemsServiceImpl.class);

    @Override
    public void testRedis() {

            String pong = redisTemplate.getConnectionFactory().getConnection().ping();
            System.out.println("Redis ping response: " + pong);

    }

    @Override
    public List<CartItemsRes> getCart(Long userId) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(buildKey(userId));
        List<CartItemsRes> cart = new ArrayList<>();
//        for (Object value : entries.values()) {
//            cart.add((CartItemsRes) value);
//        }
        for (Object value : entries.values()) {
            // Convert LinkedHashMap to CartItemsRes
            CartItemsRes item = objectMapper.convertValue(value, CartItemsRes.class);

            logger.info("Cart item: {}", item);
            cart.add(item);
        }
        return cart;
    }

    @Override
    public void addToCart(Long userId, CartItemsReq cart) {
        String key = buildKey(userId);
        String itemKey = String.valueOf(cart.getBookId());

        Object raw = redisTemplate.opsForHash().get(key, itemKey);
        int quantity = cart.getQuantity();

        if (raw != null) {
            CartItemsRes existing = objectMapper.convertValue(raw, CartItemsRes.class);
            quantity += existing.getQuantity();


        }

        CartItemsRes cartItem = new CartItemsRes(
                cart.getBookId(),
                cart.getBookTitle(),
                cart.getPrice(),
                cart.getBookImage(),
                quantity
        );

        redisTemplate.opsForHash().put(key, itemKey, cartItem);

        System.out.println("Saving to Redis with key: " + key + ", itemKey: " + itemKey);
        System.out.println("Cart Item: " + cartItem);

        // Setelah update Redis, publish event ke Kafka
        CartEvent event = new CartEvent();
        event.setUserId(userId);
        event.setBookId(cart.getBookId());
        event.setQuantity(quantity);
        event.setAction("add");

        try {
            String eventStr = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("cart-events-topic", eventStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromCart(Long userId, Long bookId) {
        redisTemplate.opsForHash().delete(buildKey(userId), String.valueOf(bookId));

        // Step 2: Kirim event ke Kafka
        try {
            CartEvent event = new CartEvent();
            event.setUserId(userId);
            event.setBookId(bookId);
            event.setAction("remove");

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("cart-events-topic", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearCart(Long userId) {
        redisTemplate.delete(buildKey(userId));

        try {
            CartEvent event = new CartEvent();
            event.setUserId(userId);
            event.setAction("clear");

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("cart-events-topic", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateQuantity(Long userId, Long bookId, Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        String key = buildKey(userId);
        String itemKey = String.valueOf(bookId);

        Object raw = redisTemplate.opsForHash().get(key, itemKey);
        if (raw != null) {
            CartItemsRes existing = objectMapper.convertValue(raw, CartItemsRes.class);
            existing.setQuantity(quantity); // update quantity baru
            redisTemplate.opsForHash().put(key, itemKey, existing);

            // Kirim event Kafka
            try {
                CartEvent event = new CartEvent();
                event.setUserId(userId);
                event.setBookId(bookId);
                event.setQuantity(quantity);
                event.setAction("update");

                String message = objectMapper.writeValueAsString(event);
                kafkaTemplate.send("cart-events-topic", message);
            } catch (Exception e) {
                e.printStackTrace(); // log error
            }
        } else {
            throw new RuntimeException("Item not found in cart");
        }
    }


}
