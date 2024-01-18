package com.example.bank.mappers.transaction;

import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface TransactionListMapper {

    List<TransactionDto> toDtoList(List<Transaction> transactions);
}
