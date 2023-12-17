package com.example.db_mai.repository;

import com.example.db_mai.model.ComponentOrder;
import com.example.db_mai.model.ComponentOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentOrderRepository extends JpaRepository<ComponentOrder, ComponentOrderId> {
}
