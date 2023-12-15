package com.example.db_mai.repository;

import com.example.db_mai.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    @Query( "SELECT c FROM Component c WHERE LOWER(c.sku) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(c.model) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Component> findBySkuOrModel(@Param("searchTerm") String searchTerm);
}
