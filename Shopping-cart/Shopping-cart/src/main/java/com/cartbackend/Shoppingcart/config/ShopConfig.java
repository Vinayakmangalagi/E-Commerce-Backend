package com.cartbackend.Shoppingcart.config;

import com.cartbackend.Shoppingcart.dto.ImageDto;
import com.cartbackend.Shoppingcart.dto.ProductDto;
import com.cartbackend.Shoppingcart.model.Image;
import com.cartbackend.Shoppingcart.model.Product;
import com.cartbackend.Shoppingcart.repository.ImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ShopConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
