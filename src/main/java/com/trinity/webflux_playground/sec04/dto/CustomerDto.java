package com.trinity.webflux_playground.sec04.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Integer id;
    @NotNull(message = "{dto.customer.name.NotNull}")
    private String name;
    @Pattern(regexp = "([a-z])+@([a-z])+\\.com", message = "{dto.customer.email.pattern}")
    private String email;

    public CustomerDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
