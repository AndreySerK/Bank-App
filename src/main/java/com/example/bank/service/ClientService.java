package com.example.bank.service;

import com.example.bank.dto.client.ChangeClientDto;
import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.Client;
import com.example.bank.entity.enums.ClientStatus;
import com.example.bank.mappers.client.ClientListMapper;
import com.example.bank.mappers.client.ClientMapper;
import com.example.bank.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientListMapper clientListMapper;


    public List<ClientDto> getAllClients() {
        return clientListMapper.toDtoList(clientRepository.findAll());
    }

    public ClientDto getClientById(Integer id) {
        return clientMapper
                .toDto(clientRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Client with id = " + id + " not found")));
    }

    @Transactional
    public ClientDto createClient(@Valid ClientDto dto) {

        Client newClient = clientMapper.toEntity(dto);
        clientRepository.save(newClient);
        return clientMapper.toDto(newClient);
    }

    public List<ClientDto> getClientsByStatus(ClientStatus status) {
        List<ClientDto> clientDTOList = clientListMapper
                .toDtoList(clientRepository
                        .findClientsByStatus(status));

        if (clientDTOList.isEmpty()) {
            throw new NullPointerException("There are no clients with such status!");
        }

        return clientDTOList;
    }

    @Transactional
    public ClientDto changeClientById(@Valid ChangeClientDto dto, Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Client with id = " + id + " not found")
        );

        client.setAddress(dto.getAddress());
        client.setEmail(dto.getEmail());
        client.setStatus(dto.getStatus());
        client.setPhone(dto.getPhone());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setTaxCode(dto.getTaxCode());

        clientRepository.save(client);
        return clientMapper.toDto(client);
    }
}
