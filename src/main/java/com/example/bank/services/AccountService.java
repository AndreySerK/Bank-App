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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountListMapper accountListMapper;
    private final ClientRepository clientRepository;

    public List<AccountDto> getAllAccounts() {
       return accountListMapper.toDtoList(accountRepository.findAll());
    }

    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Account with such id not found"));
    }

    public Account addAccount(AddAccountDto dto) {
        if (dto.getClientId() == null) {
            throw new NullPointerException("ClientId cannot be null");
        }

        if (dto.getBalance() < 0.0) {
            throw new IllegalArgumentException("Balance cannot be less then 0");
        }

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

    public Account changeAccountById(ChangeAccountDto dto, Integer id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Account with such id not found")
        );

        accountRepository.deleteById(id);
        account.setName(dto.getName());
        account.setBalance(dto.getBalance());
        account.setStatus(dto.getStatus());
        account.setType(dto.getType());
        account.setCurrencyCode(dto.getCurrencyCode());

        return accountRepository.save(account);
    }
}
