package com.example.bank.mapper;

import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.Transaction;
import com.example.bank.mappers.transaction.TransactionListMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TransactionListMapperTest {

    @Autowired
    TransactionListMapper transactionListMapper;

    @Test
    public void toDtoListTest_whenGetTransactionList_shouldReturnTransactionDtoList () {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(createTransaction(1L));

        List<TransactionDto> expectedTransactionDtoList = new ArrayList<>();
        expectedTransactionDtoList.add(createTransactionDto(1L));

        List<TransactionDto> actualTransactionDtoList = transactionListMapper.toDtoList(transactionList);

        assertEquals(expectedTransactionDtoList, actualTransactionDtoList);
    }
}
