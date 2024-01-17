package com.example.bank;

import com.example.bank.dto.account.AccountDto;
import com.example.bank.dto.account.AddAccountDto;
import com.example.bank.dto.account.ChangeAccountDto;
import com.example.bank.dto.account.ClientDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.Client;
import com.example.bank.entity.Manager;
import com.example.bank.entity.enums.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.MessageFormat;

public class TestUtils {

    public static String readStringFromResource (String resourcePath) {

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(MessageFormat.format("classpath:{0}" , resourcePath));

        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Account createAccount (Long id, Client client) {

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

    public static Client createClient (Long id, Manager manager) {

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

    public static Manager createManager (Long id) {

        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setStatus(ManagerStatus.valueOf("WORKING"));
        manager.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        manager.setUpdatedAt(null);
        return manager;
    }

    public static AccountDto createAccountDto (Long id, ClientDto clientDto) {

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

    public static AddAccountDto getAddAccountDto (int clientId) {

        AddAccountDto accountDto = new AddAccountDto();
        accountDto.setName("MyAccount");
        accountDto.setType(AccountType.CURRENT);
        accountDto.setStatus(AccountStatus.ACTIVE);
        accountDto.setBalance(50);
        accountDto.setCurrencyCode(CurrencyCode.USD);
        accountDto.setCreatedAt(Timestamp.valueOf("2024-02-01 00:00:00"));
        accountDto.setClientId(clientId);

        return accountDto;
    }
    public static AccountDto getChangedAccountDto (Long id, ChangeAccountDto changeAccountDto) {

        AccountDto accountDto = createAccountDto(id, createClientDto(1L));
        accountDto.setName(changeAccountDto.getName());
        accountDto.setType(changeAccountDto.getType());
        accountDto.setStatus(changeAccountDto.getStatus());
        accountDto.setBalance(changeAccountDto.getBalance());
        accountDto.setCurrencyCode(changeAccountDto.getCurrencyCode());

        return accountDto;
    }

    public static ClientDto createClientDto (Long id) {

        ClientDto clientDto = new ClientDto();

        clientDto.setFirstName("Mark");
        clientDto.setLastName("Smith");
        clientDto.setEmail("mark@gmail.com");
        clientDto.setPhone("19990006677");

        return clientDto;
    }
}
