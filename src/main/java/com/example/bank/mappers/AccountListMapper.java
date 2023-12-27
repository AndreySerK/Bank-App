package com.example.bank.mappers;

import com.example.bank.dto.AccountDto;
import com.example.bank.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface AccountListMapper {

    List<AccountDto> toDtoList(List<Account> accountList);
}
