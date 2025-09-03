package com.trinity.webflux_playground.sec02.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDetails(UUID orderId, String customerName, String productName, Integer amount, Instant orderDate) {
}
