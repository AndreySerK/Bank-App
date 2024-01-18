package com.example.bank.mappers.transaction;

import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

@Mapper(componentModel = "spring", imports = Timestamp.class)
public interface TransactionMapper {

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Transaction toEntity(TransactionDto transactionDto);

    @Mapping(source = "transaction.debitAccount.id", target = "debitAccountId")
    @Mapping(source = "transaction.creditAccount.id", target = "creditAccountId")
    TransactionDto toDto(Transaction transaction);
}