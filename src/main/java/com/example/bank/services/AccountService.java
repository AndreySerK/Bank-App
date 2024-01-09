package com.example.bank.services;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.AddAccountDto;
import com.example.bank.dto.ChangeAccountDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.Client;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.mappers.AccountListMapper;
import com.example.bank.mappers.AccountMapper;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Validated
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountListMapper accountListMapper;
    private final ClientRepository clientRepository;

    @Transactional
    public List<AccountDto> getAllAccounts() {
       return accountListMapper.toDtoList(accountRepository.findAll());
    }

    @Transactional
    public AccountDto getAccountById(Integer id) {
        return accountMapper
                .toDto(accountRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Account with such id not found")));
    }

    @Transactional
    public Account addAccount(@Valid AddAccountDto dto) {

        Integer id = dto.getClientId();
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Client with such id not found")
        );
        Account newAccount = accountMapper.toEntity(dto);
        newAccount.setClient(client);
        return accountRepository.save(newAccount);
    }

    public List<AccountDto> getAccountsByStatus(AccountStatus status) {
        List<AccountDto> accountDTOList = accountListMapper
                .toDtoList(accountRepository
                        .findAccountsByStatus(status));

        if (accountDTOList.isEmpty()) {
            throw new NullPointerException("There are no accounts with such status!");
        }

        return accountDTOList;
    }

    @Transactional
    public void changeAccountById(@Valid ChangeAccountDto dto, Integer id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Account with such id not found")
        );

        account.setName(dto.getName());
        account.setBalance(dto.getBalance());
        account.setStatus(dto.getStatus());
        account.setType(dto.getType());
        account.setCurrencyCode(dto.getCurrencyCode());

        accountRepository.save(account);
    }
}
