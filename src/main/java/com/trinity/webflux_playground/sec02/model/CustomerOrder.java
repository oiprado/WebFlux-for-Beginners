package com.trinity.webflux_playground.sec02.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("customer_order")
public class CustomerOrder {
    @Id
    private UUID orderId;
//    @Column("customer_id")
    private Integer customerid;
//    @Column("product_id")
    private Integer productId;
    private Integer amount;
    private LocalDateTime orderDate;
}
