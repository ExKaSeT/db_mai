package com.example.db_mai.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders_components")
@Data
public class ComponentOrder {

    @EmbeddedId
    private ComponentOrderId componentOrderId;

    @Column
    private Integer quantity;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "component_id")
    private Component component;
}
