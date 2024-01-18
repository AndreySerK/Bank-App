package com.example.bank.service;

import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.Transaction;
import com.example.bank.entity.enums.TransactionType;
import com.example.bank.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bank.TestUtils.createTransaction;
import static com.example.bank.TestUtils.createTransactionDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;


    @Test
    public void getAllTransactionsTest_whenFindAll_thenReturnAllTransactions() throws Exception {

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(createTransaction(1L));
        transactionList.add(createTransaction(2L));

        when(transactionRepository.findAll()).thenReturn(transactionList);

        List<TransactionDto> actualList = transactionService.getAllTransactions();

        assertEquals(2, actualList.size());
    }

    @Test
    public void getTransactionByIdTest_whenGetTransactionById_thenReturnTransactionById() throws Exception {

        Transaction transaction = createTransaction(1L);
        TransactionDto expectedTransactionDto = createTransactionDto(1L);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        TransactionDto actualTransactionDto = transactionService.getTransactionById(1);
        assertEquals(expectedTransactionDto, actualTransactionDto);
    }

    @Test
    public void createTransactionTest_whenCreateTransaction_thenReturnNewTransaction() throws Exception {

        Transaction transaction = createTransaction(1L);
        TransactionDto addTransactionDto = createTransactionDto(1L);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        TransactionDto actualTransactionDto = transactionService.createTransaction(addTransactionDto);

        assertNotNull(actualTransactionDto);
    }

    @Test
    public void getTransactionByTypeTest_whenGetTransactionByType_thenReturnTransactionByType() throws Exception {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(createTransaction(1L));
        transactionList.add(createTransaction(2L));


        when(transactionRepository.findAllByType(TransactionType.TRANSFER)).thenReturn(transactionList);

        List<TransactionDto> transactionDtoList = transactionService.getTransactionsByType(TransactionType.TRANSFER);

        assertEquals(2, transactionDtoList.size());
    }

    @Test
    public void getTransactionsByClientIdTest_whenGetTransactionsByClientId_thenReturnTransactionsByClientId() throws Exception {

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(createTransaction(1L));
        transactionList.add(createTransaction(2L));


        when(transactionRepository.findAllByClientId(1L)).thenReturn(transactionList);

        List<TransactionDto> transactionDtoList = transactionService.getTransactionsByClientId(1L);

        assertEquals(2, transactionDtoList.size());
    }


    @Test
    public void getTransactionByIdTest_whenFindByIdNotExistedTransaction_thenReturnException() throws Exception {

        when(transactionRepository.findById(500)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> transactionService.getTransactionById(500));
        verify(transactionRepository, times(1)).findById(500);
    }

    @Test
    public void getTransactionsByStatusTest_whenFindByStatusNotExistedTransactions_thenReturnException() throws Exception {

        when(transactionRepository.findAllByType(TransactionType.WITHDRAWAL)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> transactionService.getTransactionsByType(TransactionType.WITHDRAWAL));
        verify(transactionRepository, times(1)).findAllByType(TransactionType.WITHDRAWAL);
    }
}

