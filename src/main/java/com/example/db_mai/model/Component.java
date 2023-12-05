package com.example.db_mai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "components")
@Data
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "component_seq")
    @SequenceGenerator(name = "component_seq", sequenceName = "component_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String sku;

    @Column
    private String model;

    @Column
    private Integer quantity;

    @Column
    private Double price;

    @OneToMany(mappedBy = "component")
    @PrimaryKeyJoinColumn
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ComponentOrder> componentOrderList;
}
