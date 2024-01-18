package com.example.bank.repositories;

import com.example.bank.entity.Transaction;
import com.example.bank.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM transactions WHERE debit_account_id in (\n" +
            "SELECT id FROM accounts WHERE client_id = :clientId\n" +
            ");")
    List<Transaction> findAllByClientId (Long clientId);

    List<Transaction> findAllByType(TransactionType type);

    Transaction findTransactionById (Long id);
}
