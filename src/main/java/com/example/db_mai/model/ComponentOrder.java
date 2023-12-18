package com.example.db_mai.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders_components")
@IdClass(ComponentOrderId.class)
@Data
public class ComponentOrder {
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Id
    @Column(name = "component_id")
    private Long componentId;

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
