package com.example.bank.mappers.client;

import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.Client;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface ClientListMapper {

    List<ClientDto> toDtoList(List<Client> clientList);
}
