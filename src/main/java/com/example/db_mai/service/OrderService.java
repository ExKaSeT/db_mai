package com.example.db_mai.service;

import com.example.db_mai.dto.order.ComponentOrderInfoDto;
import com.example.db_mai.exception.EntityNotFoundException;
import com.example.db_mai.model.ComponentOrder;
import com.example.db_mai.model.Order;
import com.example.db_mai.repository.ComponentOrderRepository;
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
    private final ComponentOrderRepository componentOrderRepository;

    public List<Order> getAll(Long customerId) {
        var customer = customerService.get(customerId);
        return orderRepository.getOrdersByCustomer(customer);
    }

    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public List<ComponentOrderInfoDto> getComponentOrderInfo(List<ComponentOrder> components) {
        List<ComponentOrderInfoDto> result = new ArrayList<>();
        for (var componentOrder : components) {
            var component = componentOrder.getComponent();
            var dto = new ComponentOrderInfoDto();
            dto.setSku(component.getSku()).setPrice(component.getPrice()).setModel(component.getModel())
                    .setQuantity(componentOrder.getQuantity());
            result.add(dto);
        }
        return result;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Order create(Long customerId, Cart cart) {
        if (cart.isEmpty()) {
            throw new ValidationException("Корзина пуста");
        }
        var customer = customerService.get(customerId);
        var order = new Order();
        order.setCustomer(customer);
        order = orderRepository.save(order);
        List<ComponentOrder> components = new ArrayList<>();
        for (var componentQuantity : cart.values()) {
            var component = componentService.get(componentQuantity.getItemId());
            component = componentService.updateQuantity(component, component.getQuantity() - componentQuantity.getQuantity());
            var componentOrder = new ComponentOrder();
            componentOrder.setOrderId(order.getId());
            componentOrder.setComponentId(component.getId());
            componentOrder.setQuantity(componentQuantity.getQuantity());
            components.add(componentOrder);
        }
        order.setComponentOrderList(components);
        order = orderRepository.save(order);

        cart.clear();
        return order;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void cancel(Long orderId) {
        var order = this.get(orderId);
        var componentsInOrder = componentOrderRepository.findAllByOrderId(orderId);
        for (var componentOrder : componentsInOrder) {
            var component = componentService.get(componentOrder.getComponentId());
            componentService.updateQuantity(component, component.getQuantity() + componentOrder.getQuantity());
            componentOrderRepository.delete(componentOrder);
        }
        orderRepository.delete(order);
    }
}
