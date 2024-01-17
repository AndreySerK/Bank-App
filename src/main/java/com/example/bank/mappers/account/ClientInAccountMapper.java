package com.example.bank.mappers.account;

import com.example.bank.dto.account.ClientInAccountDto;
import com.example.bank.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientInAccountMapper {
    ClientInAccountDto toDto (Client client);
}
