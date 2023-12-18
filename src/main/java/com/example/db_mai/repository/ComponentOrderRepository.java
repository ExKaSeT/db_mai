package com.example.db_mai.repository;

import com.example.db_mai.model.ComponentOrder;
import com.example.db_mai.model.ComponentOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentOrderRepository extends JpaRepository<ComponentOrder, ComponentOrderId> {
    List<ComponentOrder> findAllByOrderId(Long orderId);
}
