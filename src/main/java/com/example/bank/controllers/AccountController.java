package com.example.bank.controllers;

import com.example.bank.dto.AccountDto;
import com.example.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
