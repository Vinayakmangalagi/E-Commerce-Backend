package com.cartbackend.Shoppingcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class CartConfig {

    @Bean
    public AtomicLong cartIdGenerator() {
        return new AtomicLong();
    }

}
