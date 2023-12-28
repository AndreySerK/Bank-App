package com.example.bank.controllers;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.AddAccountDto;
import com.example.bank.dto.ChangeAccountDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.mappers.AccountMapper;
import com.example.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;


    @GetMapping ("/api/accounts/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> accountList () {
        return accountService.getAllAccounts();
    }

    @GetMapping ("/api/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto accountById (@PathVariable Integer id) {
        return accountService.getAccountById(id);
    }

    @PostMapping ("/api/account/post")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> addAccount (@RequestBody AddAccountDto account) {
        Account account1 = accountService.addAccount(account);
        return ResponseEntity.ok(accountMapper.toDto(account1));
    }

    @GetMapping ("/api/accounts/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> accountsByStatus (@PathVariable AccountStatus status) {
        return accountService.getAccountsByStatus(status);
    }

    @PutMapping ("/api/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AccountDto> changeAccountById (@RequestBody ChangeAccountDto account,
                                      @PathVariable Integer id) {
        accountService.changeAccountById(account, id);
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
}