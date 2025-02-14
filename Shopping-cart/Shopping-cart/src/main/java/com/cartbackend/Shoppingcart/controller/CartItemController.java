package com.cartbackend.Shoppingcart.controller;

import com.cartbackend.Shoppingcart.exception.ResourceNotFoundException;
import com.cartbackend.Shoppingcart.model.Cart;
import com.cartbackend.Shoppingcart.model.User;
import com.cartbackend.Shoppingcart.response.ApiResponse;
import com.cartbackend.Shoppingcart.service.cart.CartItemService;
import com.cartbackend.Shoppingcart.service.cart.CartService;
import com.cartbackend.Shoppingcart.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(value = "${api.prefix}/cartItems")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId,@RequestParam Integer quantity){
        try {
              User user = userService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId()
                    ,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Add item success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping(value = "/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long itemId){
        try {
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Removed item success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping(value = "/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,@PathVariable Long itemId,@RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuantity(cartId,itemId,quantity);
            return ResponseEntity.ok(new ApiResponse("Update item success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
