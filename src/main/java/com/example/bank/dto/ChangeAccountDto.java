package com.example.bank.dto;

import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ChangeAccountDto {

    private String name;

    private AccountStatus status;

    private AccountType type;

    private CurrencyCode currencyCode;

    private double balance;
}
