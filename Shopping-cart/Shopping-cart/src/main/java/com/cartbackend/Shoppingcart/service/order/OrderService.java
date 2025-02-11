package com.cartbackend.Shoppingcart.service.order;

import com.cartbackend.Shoppingcart.dto.OrderDto;
import com.cartbackend.Shoppingcart.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
