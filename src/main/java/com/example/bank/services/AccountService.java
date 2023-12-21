package com.example.bank.services;

import com.example.bank.dto.AccountDto;
import com.example.bank.mappers.AccountListMapper;
import com.example.bank.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountListMapper accountListMapper;

    public List<AccountDto> getAllAccounts() {
       return accountListMapper.toDtoList(accountRepository.findAll());
    }
}
