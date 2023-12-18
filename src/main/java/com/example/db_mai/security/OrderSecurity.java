package com.example.db_mai.security;

import com.example.db_mai.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderSecurity {
    private final OrderService orderService;

    public boolean isAllowedToModifyOrder(Authentication auth, Long orderId) {
        var order = orderService.get(orderId);
        UserInfoDetails user = (UserInfoDetails) auth.getPrincipal();
        return order.getCustomer().getId().equals(user.getCustomer().getId());
    }
}
