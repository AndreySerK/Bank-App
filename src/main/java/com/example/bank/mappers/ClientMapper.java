package com.example.bank.mappers;

import com.example.bank.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toDto (Client client);
}
