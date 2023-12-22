package com.example.bank.dto;

import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AddAccountDto {

    private String name;

    private AccountType type;

    private AccountStatus status;

    private double balance;

    private CurrencyCode currencyCode;

    private Integer clientId;

    private Timestamp createdAt;
}
