package com.example.bank.service;

import com.example.bank.dto.client.ChangeClientDto;
import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.Client;
import com.example.bank.entity.enums.ClientStatus;
import com.example.bank.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientServiceTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;


    @Test
    public void getAllClientsTest_whenFindAll_thenReturnAllClients() throws Exception {

        List<Client> clientList = new ArrayList<>();
        clientList.add(createClient(1L,createManager(1L)));
        clientList.add(createClient(2L,createManager(2L)));

        when(clientRepository.findAll()).thenReturn(clientList);

        List<ClientDto> actualList =  clientService.getAllClients();

        assertEquals(2, actualList.size());
    }

    @Test
    public void getClientByIdTest_whenGetClientById_thenReturnClientById() throws Exception {

        Client client = createClient(1L,createManager(1L));
        ClientDto expectedClientDto = createClientDto(1L, 1L);

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        ClientDto actualClientDto =  clientService.getClientById(1);
        assertEquals(expectedClientDto, actualClientDto);
    }

    @Test
    public void createClientTest_whenCreateClient_thenReturnNewClient() throws Exception {

        Client client = createClient(1L,createManager(1L));
        ClientDto clientDto = createClientDto(1L,1L);

        when(clientRepository.save(any(Client.class))).thenReturn(client);
        ClientDto actualClientDto =  clientService.createClient(clientDto);

        assertNotNull(actualClientDto);
    }

    @Test
    public void getClientByStatusTest_whenGetClientByStatus_thenReturnClientByStatus() throws Exception {
        List<Client> cLientList = new ArrayList<>();
        cLientList.add(createClient(1L,createManager(1L)));
        cLientList.add(createClient(2L,createManager(2L)));


        when(clientRepository.findClientsByStatus(ClientStatus.ACTIVE)).thenReturn(cLientList);

        List<ClientDto> clientDtoList =  clientService.getClientsByStatus(ClientStatus.ACTIVE);

        assertEquals(2, clientDtoList.size());
    }

    @Test
    public void changeClientByIdTest_whenChangeClient_thenReturnUpdatedClient() throws Exception {

        Client client = createClient(1L,createManager(1L));

        ChangeClientDto changeClientDto = new ChangeClientDto(
                "Mark",
                "Smith",
                "5658380715",
                ClientStatus.BLOCKED,
                "mark@gmail.com",
                "13791260996",
                "18485 Ronald Tunnel, London",
                1L
        );

        when(clientRepository.findById(any(Integer.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDto updatedClientDto = clientService.changeClientById(changeClientDto, 1);

        assertEquals(changeClientDto.getFirstName(), updatedClientDto.getFirstName());
    }

    @Test
    public void getClientByIdTest_whenFindByIdNotExistedClient_thenReturnException() throws Exception {

        when(clientRepository.findById(500)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(500));
        verify(clientRepository, times(1)).findById(500);
    }

    @Test
    public void getClientByStatusTest_whenFindByStatusNotExistedClients_thenReturnException() throws Exception {

        when(clientRepository.findClientsByStatus(ClientStatus.DECEASED)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> clientService.getClientsByStatus(ClientStatus.DECEASED));
        verify(clientRepository, times(1)).findClientsByStatus(ClientStatus.DECEASED);
    }
}

