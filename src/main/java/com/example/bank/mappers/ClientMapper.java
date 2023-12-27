package com.example.bank.mappers;

import com.example.bank.dto.ClientDto;
import com.example.bank.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto (Client client);
}
