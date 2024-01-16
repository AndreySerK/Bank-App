package com.example.bank.controllers;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.ChangeAccountDto;
import com.example.bank.dto.CreateAccountDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.mappers.AccountMapper;
import com.example.bank.response.accounts.GetAllAccountsResponse;
import com.example.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;


    @GetMapping ("/api/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public GetAllAccountsResponse getAll () {
        return new GetAllAccountsResponse(accountService.getAllAccounts());
    }

    @GetMapping ("/api/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getById (@PathVariable Integer id) {
        return accountService.getAccountById(id);
    }

    @PostMapping ("/api/account/post")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> create (@RequestBody CreateAccountDto account) {
        Account account1 = accountService.createAccount(account);
        return ResponseEntity.ok(accountMapper.toDto(account1));
    }

    @GetMapping ("/api/accounts/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getByStatus (@PathVariable AccountStatus status) {
        return accountService.getAccountsByStatus(status);
    }

    @PutMapping ("/api/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> changeById (@RequestBody ChangeAccountDto account,
                                      @PathVariable Integer id) {
        accountService.changeAccountById(account, id);
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
}