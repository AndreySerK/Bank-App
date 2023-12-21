package com.example.bank.dto;

import com.example.bank.entity.Client;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AccountDto {

    private String name;

    private AccountType type;

    private AccountStatus status;

    private double balance;

    private CurrencyCode currencyCode;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private ClientDto client;
}
