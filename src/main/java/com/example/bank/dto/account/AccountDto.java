package com.example.bank.dto.account;


import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, max = 30, message = "Name length must be from 3 to 30 symbols")
    private String name;

    @NotNull(message = "The field must not be empty")
    private AccountType type;

    @NotNull(message = "The field must not be empty")
    private AccountStatus status;

    @NotNull(message = "The field must not be empty")
    @PositiveOrZero(message = "The field must be positive or zero")
    private double balance;

    @NotNull(message = "The field must not be empty")
    private CurrencyCode currencyCode;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @NotNull(message = "The field must not be empty")
    private ClientInAccountDto client;
}
