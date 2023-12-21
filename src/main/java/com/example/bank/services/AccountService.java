package com.example.bank.services;

import com.example.bank.dto.AccountDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
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

    public Account getAccountById(Integer id) {
        return accountRepository.getAccountById(Long.valueOf(id));
    }

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<AccountDto> getAccountsByStatus(AccountStatus status) {
        return accountListMapper
                .toDtoList(accountRepository
                        .findAccountsByStatus(status));
    }
}
