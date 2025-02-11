package com.cartbackend.Shoppingcart.service.cart;

import com.cartbackend.Shoppingcart.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void cleanCart(Long id);
    BigDecimal getTotalPrice(Long id);

  Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
