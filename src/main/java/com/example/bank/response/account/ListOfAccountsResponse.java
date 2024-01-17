package com.example.bank.response.account;

import com.example.bank.dto.account.AccountDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfAccountsResponse {

    private final List<AccountDto> accounts;
}
