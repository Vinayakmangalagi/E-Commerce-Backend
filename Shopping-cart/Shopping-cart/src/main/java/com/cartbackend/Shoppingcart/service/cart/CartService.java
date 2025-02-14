package com.cartbackend.Shoppingcart.service.cart;

import com.cartbackend.Shoppingcart.model.Cart;
import com.cartbackend.Shoppingcart.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void cleanCart(Long id);
    BigDecimal getTotalPrice(Long id);

  Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
