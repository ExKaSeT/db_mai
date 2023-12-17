package com.example.db_mai.service;

import com.example.db_mai.model.ComponentOrder;
import com.example.db_mai.model.ComponentOrderId;
import com.example.db_mai.model.Order;
import com.example.db_mai.repository.OrderRepository;
import com.example.db_mai.util.Cart;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ComponentService componentService;

    public List<Order> getAll(Long customerId) {
        var customer = customerService.get(customerId);
        return orderRepository.getOrdersByCustomer(customer);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Order create(Long customerId, Cart cart) {
        if (cart.isEmpty()) {
            throw new ValidationException("Корзина пуста");
        }
        var customer = customerService.get(customerId);
        var order = new Order();
        order.setCustomer(customer);
        List<ComponentOrder> components = new ArrayList<>();
        for (var componentQuantity : cart.values()) {
           var component = componentService.get(componentQuantity.getItemId());
           componentService.updateQuantity(component, component.getQuantity() - componentQuantity.getQuantity());
           var componentOrder = new ComponentOrder();
           var componentOrderId = new ComponentOrderId();
           componentOrderId.setOrderId(order.getId());
           componentOrderId.setComponentId(component.getId());
           componentOrder.setComponentOrderId(componentOrderId);
           componentOrder.setOrder(order);
           componentOrder.setComponent(component);
           componentOrder.setQuantity(componentOrder.getQuantity());
           components.add(componentOrder);
        }
        order.setComponentOrderList(components);
        return orderRepository.save(order);
    }
}
