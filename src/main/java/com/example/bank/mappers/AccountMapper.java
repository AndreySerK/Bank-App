package com.example.bank.mappers;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.CreateAccountDto;
import com.example.bank.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

@Mapper(componentModel = "spring", imports = Timestamp.class)
public interface AccountMapper {

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Account toEntity (CreateAccountDto account);

    AccountDto toDto (Account account);
}
