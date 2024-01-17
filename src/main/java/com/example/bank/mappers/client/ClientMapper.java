package com.example.bank.mappers.client;

import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

@Mapper(componentModel = "spring", imports = Timestamp.class)
public interface ClientMapper {

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Client toEntity(ClientDto clientDto);

    @Mapping(source = "client.manager.id", target = "managerId")
    ClientDto toDto(Client client);
}
