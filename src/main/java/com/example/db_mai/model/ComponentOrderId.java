package com.example.db_mai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentOrderId implements Serializable {
    private Long componentId;
    private Long orderId;
}
