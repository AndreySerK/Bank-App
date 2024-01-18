package com.example.bank.repository;

import com.example.bank.entity.Transaction;
import com.example.bank.entity.enums.TransactionType;
import com.example.bank.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void findTransactionByIdTest_whenFoundTransactionById_thenReturnTransactionById() throws Exception {

        Transaction transaction = createTransaction(1L);

        transactionRepository.save(transaction);

        Transaction transactionDB = transactionRepository.findById(1).get();

        assertThat(transactionDB).isNotNull();
        assertEquals(transaction.getType(), transactionDB.getType());
    }

    @Test
    public void findTransactionsByTypeTest_whenFoundTransactionsByType_thenReturnTransactionsByType() throws Exception {

        List<Transaction> actualList = transactionRepository.findAllByType(TransactionType.TRANSFER);

        assertThat(actualList).isNotNull();
        assertEquals(2, actualList.size());
    }

    @Test
    public void findTransactionsByClientIdTest_whenFoundTransactionsByClientId_thenReturnTransactionsByClientId() throws Exception {

        List<Transaction> actualList = transactionRepository.findAllByClientId(1L);

        assertThat(actualList).isNotNull();
        assertEquals(1, actualList.size());
    }

    @Test
    public void findAllTransactionsTest_whenFoundAllTransactions_thenReturnAllTransactions() throws Exception {

        List<Transaction> actualList = transactionRepository.findAll();

        assertThat(actualList).isNotNull();
        assertEquals(2, actualList.size());
    }
}