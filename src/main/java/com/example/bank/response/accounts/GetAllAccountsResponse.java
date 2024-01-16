package com.example.bank.response.accounts;

import com.example.bank.dto.AccountDto;
import lombok.Data;

import java.util.List;

@Data
public class GetAllAccountsResponse {

    private final List<AccountDto> accounts;
}
