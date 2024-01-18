package com.example.bank.mapper;

import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.Client;
import com.example.bank.mappers.client.ClientListMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClientListMapperTest {

    @Autowired
    ClientListMapper clientListMapper;

    @Test
    public void toDtoListTest_whenGetClientList_shouldReturnClientDtoList() {
        List<Client> clientList = new ArrayList<>();
        clientList.add(createClient(1L, createManager(1L)));

        List<ClientDto> expectedClientDtoList = new ArrayList<>();
        expectedClientDtoList.add(createClientDto(1L, 1L));

        List<ClientDto> actualClientDtoList = clientListMapper.toDtoList(clientList);

        assertEquals(expectedClientDtoList, actualClientDtoList);
    }
}
