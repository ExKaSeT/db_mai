package com.example.db_mai.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDto {
    @Length(max = 50, message = "Имя слишком длинное")
    private String name;
}
