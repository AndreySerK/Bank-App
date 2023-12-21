package com.example.bank.controllers;

import com.example.bank.dto.AccountDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping ("/api/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> accountList () {
        return accountService.getAllAccounts();
    }

    @GetMapping ("/api/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account accountById (@PathVariable Integer id) {
        return accountService.getAccountById(id);
    }

    @PostMapping ("/api/account")
    @ResponseStatus(HttpStatus.OK)
    public Account addAccount (@RequestBody Account account) {
        return accountService.addAccount(account);
    }

    @GetMapping ("/api/accounts/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> accountsByStatus (@PathVariable AccountStatus status) {
        return accountService.getAccountsByStatus(status);
    }
}