package com.example.db_mai.repository;

import com.example.db_mai.model.Customer;
import com.example.db_mai.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByCustomer(Customer customer);
}
