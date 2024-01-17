package com.example.bank.controllers;

import com.example.bank.dto.account.AccountDto;
import com.example.bank.dto.account.AddAccountDto;
import com.example.bank.dto.account.ChangeAccountDto;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.response.account.ListOfAccountsResponse;
import com.example.bank.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping ("/api/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public ListOfAccountsResponse getAll () {
        return new ListOfAccountsResponse(accountService.getAllAccounts());
    }

    @GetMapping ("/api/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getById (@PathVariable Integer id) {
        return accountService.getAccountById(id);
    }

    @PostMapping ("/api/account/post")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto create (@RequestBody @Valid AddAccountDto account) {
        return accountService.createAccount(account);
    }

    @GetMapping ("/api/accounts/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ListOfAccountsResponse getByStatus (@PathVariable AccountStatus status) {
        return new ListOfAccountsResponse(accountService.getAccountsByStatus(status));
    }

    @PutMapping ("/api/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto changeById (@RequestBody ChangeAccountDto account,
                                      @PathVariable Integer id) {
        return accountService.changeAccountById(account, id);
    }
}