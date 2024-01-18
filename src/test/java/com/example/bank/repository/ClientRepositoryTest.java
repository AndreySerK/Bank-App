package com.example.bank.repository;

import com.example.bank.entity.Client;
import com.example.bank.entity.enums.ClientStatus;
import com.example.bank.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.bank.TestUtils.createClient;
import static com.example.bank.TestUtils.createManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void findClientByIdTest_whenFoundClientById_thenReturnClientById() throws Exception {

        Client client = createClient(1L, createManager(1L));

        clientRepository.save(client);

        Client clientDB = clientRepository.findById(1).get();

        assertThat(clientDB).isNotNull();
        assertEquals(client.getFirstName(), clientDB.getFirstName());
    }

    @Test
    public void findClientsByStatusTest_whenFoundClientsByStatus_thenReturnClientsByStatus() throws Exception {

        List<Client> actualList = clientRepository.findClientsByStatus(ClientStatus.ACTIVE);

        assertThat(actualList).isNotNull();
        assertEquals(2, actualList.size());
    }

    @Test
    public void findAllClientsTest_whenFoundAllClients_thenReturnAllClients() throws Exception {

        List<Client> actualList = clientRepository.findAll();

        assertThat(actualList).isNotNull();
        assertEquals(2, actualList.size());
    }
}
