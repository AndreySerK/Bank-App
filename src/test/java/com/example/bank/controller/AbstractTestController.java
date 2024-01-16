package com.example.bank.controller;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.ClientDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.Client;
import com.example.bank.entity.Manager;
import com.example.bank.entity.enums.*;
import com.example.bank.response.accounts.GetAllAccountsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected Account createAccount (Long id, Client client) {

        Account account = new Account();
        account.setId(id);
        account.setName("MyAccount");
        account.setType(AccountType.CURRENT);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(50);
        account.setCurrencyCode(CurrencyCode.USD);
        account.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        account.setUpdatedAt(null);
        account.setClient(client);
        return account;

    }

    protected Client createClient (Long id, Manager manager) {

        Client client = new Client();
        client.setId(id);
        client.setStatus(ClientStatus.ACTIVE);
        client.setTaxCode("5658380715");
        client.setFirstName("Mark");
        client.setLastName("Smith");
        client.setEmail("mark@gmail.com");
        client.setAddress("18485 Ronald Tunnel, London");
        client.setPhone("19990006677");
        client.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        client.setUpdatedAt(null);
        client.setManager(manager);
        return client;

    }

    protected Manager createManager (Long id) {

        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setStatus(ManagerStatus.valueOf("WORKING"));
        manager.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        manager.setUpdatedAt(null);
        return manager;
    }

    protected AccountDto createAccountDto (Long id, ClientDto clientDto) {

        AccountDto accountDto = new AccountDto();
        accountDto.setName("MyAccount");
        accountDto.setType(AccountType.CURRENT);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setBalance(50);
        accountDto.setCurrencyCode(CurrencyCode.USD);
        accountDto.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        accountDto.setUpdatedAt(null);
        accountDto.setClient(clientDto);

        return accountDto;
    }

    protected ClientDto createClientDto (Long id) {

        ClientDto clientDto = new ClientDto();

        clientDto.setFirstName("Mark");
        clientDto.setLastName("Smith");
        clientDto.setEmail("mark@gmail.com");
        clientDto.setPhone("19990006677");

        return clientDto;
    }

    protected GetAllAccountsResponse createResponse (List<AccountDto> accountDtoList) {
        return new GetAllAccountsResponse(accountDtoList);
    }
}
