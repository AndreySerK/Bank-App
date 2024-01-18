package com.example.bank.service;


import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.Transaction;
import com.example.bank.entity.enums.TransactionType;
import com.example.bank.mappers.transaction.TransactionListMapper;
import com.example.bank.mappers.transaction.TransactionMapper;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionListMapper transactionListMapper;
    private final AccountRepository accountRepository;


    public List<TransactionDto> getAllTransactions() {
        return transactionListMapper.toDtoList(transactionRepository.findAll());
    }

    public TransactionDto getTransactionById(Integer id) {
        return transactionMapper
                .toDto(transactionRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Transaction with id = " + id + " not found")));
    }

    @Transactional
    public TransactionDto createTransaction(@Valid TransactionDto dto) {

        Account debitAccount = accountRepository.findById(dto.getDebitAccountId());
        Account creditAccount = accountRepository.findById(dto.getCreditAccountId());
        debitAccount.setBalance(debitAccount.getBalance() - dto.getAmount());
        accountRepository.save(debitAccount);
        creditAccount.setBalance(creditAccount.getBalance() + dto.getAmount());
        accountRepository.save(creditAccount);

        Transaction newTransaction = transactionMapper.toEntity(dto);
        transactionRepository.save(newTransaction);
        return transactionMapper.toDto(newTransaction);
    }

    public List<TransactionDto> getTransactionsByType (TransactionType type) {
        List<TransactionDto> transactionDTOList = transactionListMapper
                .toDtoList(transactionRepository
                        .findAllByType(type));

        if (transactionDTOList.isEmpty()) {
            throw new NullPointerException("There are no transactions with such type!");
        }
        return transactionDTOList;
    }

    public List<TransactionDto> getTransactionsByClientId (Long clientId) {
        List<TransactionDto> transactionDTOList = transactionListMapper
                .toDtoList(transactionRepository
                        .findAllByClientId(clientId));

        if (transactionDTOList.isEmpty()) {
            throw new NullPointerException("There are no transactions with such clientId!");
        }
        return transactionDTOList;
    }
}