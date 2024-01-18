package com.example.bank.controller;

import com.example.bank.TestUtils;
import com.example.bank.dto.client.ChangeClientDto;
import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.enums.ClientStatus;
import com.example.bank.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.createClientDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest  {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    ClientService clientService;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getAllTest_whenFindAll_thenReturnAllClients() throws Exception {

        List<ClientDto> clientDtoList = new ArrayList<>();
        clientDtoList.add(createClientDto(1L,1L));
        clientDtoList.add(createClientDto(2L,2L));


        Mockito.when(clientService.getAllClients()).thenReturn(clientDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/clients/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/client/getAllClientsResponse.json");

        Mockito.verify(clientService, Mockito.times(1)).getAllClients();

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenGetClientById_thenReturnClientById() throws Exception {

        ClientDto clientDto = createClientDto(1L,1L);

        Mockito.when(clientService.getClientById(1)).thenReturn(clientDto);

        String actualResponse = mockMvc.perform(get("/auth/api/client/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/client/getClientByIdResponse.json");

        Mockito.verify(clientService, Mockito.times(1)).getClientById(1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByStatusTest_whenGetClientByStatus_thenReturnClientByStatus() throws Exception {

        List<ClientDto> clientDtoList = new ArrayList<>();
        clientDtoList.add(createClientDto(1L,1L));
        clientDtoList.add(createClientDto(2L,2L));


        Mockito.when(clientService.getClientsByStatus(ClientStatus.ACTIVE)).thenReturn(clientDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/clients/ACTIVE"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/client/getClientsByStatusResponse.json");

        Mockito.verify(clientService, Mockito.times(1)).getClientsByStatus(ClientStatus.ACTIVE);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateClient_thenReturnNewClient() throws Exception {

        ClientDto clientDto = createClientDto(1L, 1L);

        Mockito.when(clientService.createClient(clientDto)).thenReturn(clientDto);

        String actualResponse = mockMvc.perform(post("/auth/api/client/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/client/createClientResponse.json");

        Mockito.verify(clientService, Mockito.times(1)).createClient(clientDto);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void changeByIdTest_whenChangeAccount_thenReturnUpdatedAccount() throws Exception {

        ChangeClientDto changeClientDto = new ChangeClientDto(
                "Mark",
                "Smith",
                "5658380715",
                ClientStatus.BLOCKED,
                "mark@gmail.com",
                "19990006677",
                "18485 Ronald Tunnel, London",
                1L
                );
        ClientDto updatedClient = TestUtils.getChangedClientDto(1L, changeClientDto);

        Mockito.when(clientService.changeClientById(changeClientDto, 1)).thenReturn(updatedClient);

        String actualResponse = mockMvc.perform(put("/auth/api/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeClientDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/client/changeClientResponse.json");

        Mockito.verify(clientService, Mockito.times(1)).changeClientById(changeClientDto, 1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenFindByIdNotExistedAccount_thenReturnError() throws Exception {

        Mockito.when(clientService.getClientById(500)).thenThrow(new EntityNotFoundException("Client with id = 500 not found"));

        var actualResponse = mockMvc.perform(get("/auth/api/client/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/client/clientByIdNotFoundResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateClientWithEmptyFirstName_thenReturnValidationErrorResponse() throws Exception {
        ClientDto clientDto = createClientDto(1L,1L);
        clientDto.setFirstName("");

        var actualResponse = mockMvc.perform(post("/auth/api/client/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/client/createClientWithErrorsResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }
}
