package com.blackcode.book_store_be.kafka.consumer;

import com.blackcode.book_store_be.dto.cartitems.CartEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "cart-events-topic", groupId = "cart-group")
    public void listen(String message) {
        try {
            CartEvent event = objectMapper.readValue(message, CartEvent.class);
            switch (event.getAction()) {
                case "add":
                    System.out.println("Add Process Received message: " + message);
                    // Simpan ke DB
                    break;
                case "remove":
                    System.out.println("Remove Process Received message: " + message);
                    // Hapus item dari DB
                    break;
                case "update":
                    System.out.println("Update Process Received message: " + message);
                    // Update quantity di DB
                    break;
                case "clear":
                    System.out.println("Clear Process Received message: " + message);
//                    cartRepository.deleteByUserId(event.getUserId());
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
