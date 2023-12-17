package com.example.db_mai.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ComponentOrderId implements Serializable {
    @Id
    @Column(name = "component_id")
    private Long componentId;
    @Id
    @Column(name = "order_id")
    private Long orderId;
}
