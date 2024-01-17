package com.example.bank.mappers.account;

import com.example.bank.dto.account.AccountDto;
import com.example.bank.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ClientInAccountMapper.class)
public interface AccountListMapper {

    List<AccountDto> toDtoList(List<Account> accountList);
}
