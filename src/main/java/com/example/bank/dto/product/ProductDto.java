package com.example.bank.dto.product;

import com.example.bank.entity.enums.CurrencyCode;
import com.example.bank.entity.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {


    @NotBlank(message = "Name must not be blank")
    private String name;


    @NotNull(message = "The field must not be empty")
    private ProductStatus status;


    @NotNull(message = "The field must not be empty")
    private CurrencyCode currencyCode;

    @NotNull(message = "The field must not be empty")
    private int productLimit;

    @NotNull(message = "The field must not be empty")
    private double interestRate;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
