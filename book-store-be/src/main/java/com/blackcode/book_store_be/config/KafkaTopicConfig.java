package com.blackcode.book_store_be.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic cartEventsTopic() {
        return new NewTopic("cart-events-topic", 1, (short) 1);
    }

}
