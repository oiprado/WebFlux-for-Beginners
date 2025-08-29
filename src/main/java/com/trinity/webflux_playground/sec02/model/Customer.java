package com.trinity.webflux_playground.sec02.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table("customer")
public class Customer {
    @Id
    private Integer id;
    private String name;
    private String email;



}
