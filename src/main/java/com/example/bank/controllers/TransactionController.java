package com.example.bank.controllers;

import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.enums.TransactionType;
import com.example.bank.response.transaction.ListOfTransactionsResponse;
import com.example.bank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/api/transactions/all")
    @ResponseStatus(HttpStatus.OK)
    public ListOfTransactionsResponse getAll() {
        return new ListOfTransactionsResponse(transactionService.getAllTransactions());
    }

    @GetMapping("/api/transaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto getById(@PathVariable Integer id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping("/api/transaction/post")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto create(@RequestBody @Valid TransactionDto transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/api/transactions/{type}")
    @ResponseStatus(HttpStatus.OK)
    public ListOfTransactionsResponse getByStatus(@PathVariable TransactionType type) {
        return new ListOfTransactionsResponse(transactionService.getTransactionsByType(type));
    }

    @GetMapping("/api/transactions")
    @ResponseStatus(HttpStatus.OK)
    public ListOfTransactionsResponse getByClientId(@RequestParam Long clientId) {
        return new ListOfTransactionsResponse(transactionService.getTransactionsByClientId(clientId));
    }
}
