package com.cartbackend.Shoppingcart.controller;

import com.cartbackend.Shoppingcart.dto.OrderDto;
import com.cartbackend.Shoppingcart.exception.ResourceNotFoundException;
import com.cartbackend.Shoppingcart.response.ApiResponse;
import com.cartbackend.Shoppingcart.service.order.OrderServiceImpl;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/orders")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping(value = "/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = (Order) orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order placed success",order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error message",e.getMessage()));
        }
    }

    @GetMapping(value = "/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto order = (OrderDto) orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("order got success",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops",e.getMessage()));
        }
    }

    @GetMapping(value = "/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try {
             List<OrderDto> order = (List<OrderDto>) orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("order got success",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops",e.getMessage()));
        }
    }
}
