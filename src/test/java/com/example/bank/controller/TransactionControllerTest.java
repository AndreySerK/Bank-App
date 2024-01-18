package com.example.bank.controller;

import com.example.bank.TestUtils;
import com.example.bank.dto.transaction.TransactionDto;
import com.example.bank.entity.enums.TransactionType;
import com.example.bank.service.TransactionService;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.createTransactionDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    TransactionService transactionService;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getAllTest_whenFindAll_thenReturnAllTransactions() throws Exception {

        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(createTransactionDto(1L));
        transactionDtoList.add(createTransactionDto(2L));


        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactionDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/transactions/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/transaction/getAllTransactionsResponse.json");

        Mockito.verify(transactionService, Mockito.times(1)).getAllTransactions();

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenGetTransactionById_thenReturnTransactionById() throws Exception {

        TransactionDto transactionDto = createTransactionDto(1L);

        Mockito.when(transactionService.getTransactionById(1)).thenReturn(transactionDto);

        String actualResponse = mockMvc.perform(get("/auth/api/transaction/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/transaction/getTransactionByIdResponse.json");

        Mockito.verify(transactionService, Mockito.times(1)).getTransactionById(1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByTypeTest_whenGetTransactionByType_thenReturnTransactionByType() throws Exception {

        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(createTransactionDto(1L));
        transactionDtoList.add(createTransactionDto(2L));


        Mockito.when(transactionService.getTransactionsByType(TransactionType.TRANSFER)).thenReturn(transactionDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/transactions/TRANSFER"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/transaction/getTransactionsByTypeResponse.json");

        Mockito.verify(transactionService, Mockito.times(1)).getTransactionsByType(TransactionType.TRANSFER);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateTransaction_thenReturnNewTransaction() throws Exception {

        TransactionDto addTransactionDto = createTransactionDto(1L);

        Mockito.when(transactionService.createTransaction(addTransactionDto)).thenReturn(addTransactionDto);

        String actualResponse = mockMvc.perform(post("/auth/api/transaction/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addTransactionDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/transaction/createTransactionResponse.json");

        Mockito.verify(transactionService, Mockito.times(1)).createTransaction(addTransactionDto);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByClientIdTest_whenGetTransactionsByClientId_thenReturnTransactionsByClientId() throws Exception {

        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(createTransactionDto(1L));
        transactionDtoList.add(createTransactionDto(2L));


        Mockito.when(transactionService.getTransactionsByClientId(1L)).thenReturn(transactionDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/transactions?clientId=1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/transaction/getTransactionsByClientIdResponse.json");

        Mockito.verify(transactionService, Mockito.times(1)).getTransactionsByClientId(1L);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenFindByIdNotExistedTransaction_thenReturnError() throws Exception {

        Mockito.when(transactionService.getTransactionById(500)).thenThrow(new EntityNotFoundException("Transaction with id = 500 not found"));

        var actualResponse = mockMvc.perform(get("/auth/api/transaction/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/transaction/transactionByIdNotFoundResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateTransactionWithEmptyType_thenReturnValidationErrorResponse() throws Exception {
        TransactionDto newTransaction = new TransactionDto(
                null,
                10000,
                "Transfer money",
                Timestamp.valueOf("2024-02-01 00:00:00"),
                1L,
                2L);

        var actualResponse = mockMvc.perform(post("/auth/api/transaction/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTransaction)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/transaction/createTransactionWithErrorsResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }
}
