package com.example.bank.dto;


import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AccountDto {

    @NotBlank(message = "Name must be not blank")
    @Min(
            value = 3,
            message = "Name length must be greater then 3 symbols"
    )
    @Max(
            value = 30,
            message = "Name length must be less then 30 symbols")
    private String name;

    @NotNull
    private AccountType type;

    @NotNull
    private AccountStatus status;

    @NotNull
    private double balance;

    @NotNull
    private CurrencyCode currencyCode;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @NotNull
    private ClientDto client;
}
