package com.cartbackend.Shoppingcart.service.cart;

import com.cartbackend.Shoppingcart.exception.ResourceNotFoundException;
import com.cartbackend.Shoppingcart.model.Cart;
import com.cartbackend.Shoppingcart.repository.CartItemRepository;
import com.cartbackend.Shoppingcart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    @Qualifier("cartIdGenerator")
    private AtomicLong cartIdGenerator = new AtomicLong(0);

    public Long generateCartId() {
        return cartIdGenerator.incrementAndGet();
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("cart not found"));
       BigDecimal totalAmount = cart.getTotalAmount();
       cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void cleanCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();

//        getItems()
//                .stream()
//                .map(CartItem::getTotalPrice)
//                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public Long initializeNewCart(){
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {

        return cartRepository.findByUserId(userId);
    }
}
