package com.example.bank.mapper;

import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.Client;
import com.example.bank.mappers.client.ClientMapper;
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
public class ClientMapperTest {

    @Autowired
    ClientMapper clientMapper;

    Client client;
    ClientDto clientDto;

    @BeforeEach
    void setup() {
        client = createClient(1L,createManager(1L));
        clientDto = createClientDto(1L,1L);
    }

    @Test
    public void toDtoTest_whenGetClient_shouldReturnClientDto () {

        ClientDto actualClientDto = clientMapper.toDto(client);

        assertEquals(clientDto.getStatus(), actualClientDto.getStatus());
        assertEquals(clientDto.getFirstName(), actualClientDto.getFirstName());
        assertEquals(clientDto.getLastName(), actualClientDto.getLastName());
        assertEquals(clientDto.getPhone(), actualClientDto.getPhone());
        assertEquals(clientDto.getEmail(), actualClientDto.getEmail());
        assertEquals(clientDto.getTaxCode(), actualClientDto.getTaxCode());
        assertEquals(clientDto.getAddress(), actualClientDto.getAddress());
    }

    @Test
    public void toEntityTest_whenGetClientDto_shouldReturnClient () {

        Client actualClient = clientMapper.toEntity(clientDto);


        assertEquals(client.getStatus(), actualClient.getStatus());
        assertEquals(client.getFirstName(), actualClient.getFirstName());
        assertEquals(client.getLastName(), actualClient.getLastName());
        assertEquals(client.getPhone(), actualClient.getPhone());
        assertEquals(client.getEmail(), actualClient.getEmail());
        assertEquals(client.getTaxCode(), actualClient.getTaxCode());
        assertEquals(client.getAddress(), actualClient.getAddress());
    }
}
