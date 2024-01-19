package com.example.bank.mapper;

import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.Transaction;
import com.example.bank.mappers.transaction.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bank.TestUtils.createTransaction;
import static com.example.bank.TestUtils.createTransactionDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TransactionMapperTest {

    @Autowired
    TransactionMapper transactionMapper;

    Transaction transaction;
    TransactionDto transactionDto;

    @BeforeEach
    void setup() {
        transaction = createTransaction(1L);
        transactionDto = createTransactionDto(1L);
    }

    @Test
    public void toDtoTest_whenGetTransaction_shouldReturnTransactionDto () {

        TransactionDto actualTransactionDto = transactionMapper.toDto(transaction);

        assertEquals(transactionDto.getAmount(), actualTransactionDto.getAmount());
        assertEquals(transactionDto.getType(), actualTransactionDto.getType());
        assertEquals(transactionDto.getDescription(), actualTransactionDto.getDescription());
        assertEquals(transactionDto.getCreditAccountId(), actualTransactionDto.getCreditAccountId());
        assertEquals(transactionDto.getDebitAccountId(), actualTransactionDto.getDebitAccountId());
    }

    @Test
    public void toEntityTest_whenGetTransactionDto_shouldReturnTransaction () {

        Transaction actualTransaction = transactionMapper.toEntity(transactionDto);


        assertEquals(transaction.getAmount(), actualTransaction.getAmount());
        assertEquals(transaction.getType(), actualTransaction.getType());
        assertEquals(transaction.getDescription(), actualTransaction.getDescription());
    }
}
