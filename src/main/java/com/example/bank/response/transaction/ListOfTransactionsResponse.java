package com.example.bank.response.transaction;

import com.example.bank.dto.transaction.TransactionDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfTransactionsResponse {

    private final List<TransactionDto> transactions;
}
