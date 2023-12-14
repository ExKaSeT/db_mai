package com.example.db_mai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "assemblers")
@Data
public class Assembler {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assemblers_id_seq")
    @SequenceGenerator(name = "assemblers_id_seq", sequenceName = "assemblers_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private Double salary;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "weekly_hours")
    private Integer weeklyHours;
}
