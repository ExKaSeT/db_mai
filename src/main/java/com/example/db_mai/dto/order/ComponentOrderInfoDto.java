package com.example.db_mai.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ComponentOrderInfoDto {
    private String sku;
    private String model;
    private Double price;
    private Integer quantity;
}
