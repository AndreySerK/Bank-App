package com.example.bank.mapper;


import com.example.bank.dto.account.ClientInAccountDto;
import com.example.bank.entity.Client;
import com.example.bank.mappers.account.ClientInAccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClientInAccountMapperTest {

    @Autowired
    ClientInAccountMapper clientInAccountMapper;

    Client client;
    ClientInAccountDto clientInAccountDto;

    @BeforeEach
    void setup() {
        client = createClient(1L, createManager(1L));
        clientInAccountDto = createClientInAccountDto(1L);
    }

    @Test
    public void toDtoTest_whenGetClientInAccount_shouldReturnClientInAccountDto() {

        ClientInAccountDto actualClientInAccountDto = clientInAccountMapper.toDto(client);

        assertEquals(clientInAccountDto.getEmail(), actualClientInAccountDto.getEmail());
        assertEquals(clientInAccountDto.getFirstName(), actualClientInAccountDto.getFirstName());
        assertEquals(clientInAccountDto.getLastName(), actualClientInAccountDto.getLastName());
        assertEquals(clientInAccountDto.getPhone(), actualClientInAccountDto.getPhone());
    }
}
