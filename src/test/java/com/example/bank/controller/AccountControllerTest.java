package com.example.bank.controller;

import com.example.bank.TestUtils;
import com.example.bank.dto.account.AccountDto;
import com.example.bank.dto.account.AddAccountDto;
import com.example.bank.dto.account.ChangeAccountDto;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import com.example.bank.service.AccountService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.createAccountDto;
import static com.example.bank.TestUtils.createClientDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest  {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getAllTest_whenFindAll_thenReturnAllAccounts() throws Exception {

        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(createAccountDto(1L, createClientDto(1L)));
        accountDtoList.add(createAccountDto(2L, createClientDto(2L)));


        Mockito.when(accountService.getAllAccounts()).thenReturn(accountDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/accounts/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/account/getAllAccountsResponse.json");

        Mockito.verify(accountService, Mockito.times(1)).getAllAccounts();

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenGetAccountById_thenReturnAccountById() throws Exception {

        AccountDto accountDto = createAccountDto(1L, createClientDto(1L));

        Mockito.when(accountService.getAccountById(1)).thenReturn(accountDto);

        String actualResponse = mockMvc.perform(get("/auth/api/account/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/account/getAccountByIdResponse.json");

        Mockito.verify(accountService, Mockito.times(1)).getAccountById(1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByStatusTest_whenGetAccountByStatus_thenReturnAccountByStatus() throws Exception {

        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(createAccountDto(1L, createClientDto(1L)));
        accountDtoList.add(createAccountDto(2L, createClientDto(2L)));


        Mockito.when(accountService.getAccountsByStatus(AccountStatus.ACTIVE)).thenReturn(accountDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/accounts/ACTIVE"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/account/getAccountsByStatusResponse.json");

        Mockito.verify(accountService, Mockito.times(1)).getAccountsByStatus(AccountStatus.ACTIVE);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateAccount_thenReturnNewAccount() throws Exception {

        AddAccountDto addAccountDto = TestUtils.getAddAccountDto(1);
        AccountDto accountDto = createAccountDto(1L, createClientDto(1L));

        Mockito.when(accountService.createAccount(addAccountDto)).thenReturn(accountDto);

        String actualResponse = mockMvc.perform(post("/auth/api/account/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAccountDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/account/createAccountResponse.json");

        Mockito.verify(accountService, Mockito.times(1)).createAccount(addAccountDto);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void changeByIdTest_whenChangeAccount_thenReturnUpdatedAccount() throws Exception {

        ChangeAccountDto changeAccountDto = new ChangeAccountDto(
                "ChangedAccount",
                AccountStatus.INACTIVE,
                AccountType.DEPOSIT,
                CurrencyCode.CNY,
                0);
        AccountDto updatedAccount = TestUtils.getChangedAccountDto(1L, changeAccountDto);

        Mockito.when(accountService.changeAccountById(changeAccountDto, 1)).thenReturn(updatedAccount);

        String actualResponse = mockMvc.perform(put("/auth/api/account/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeAccountDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/account/changeAccountResponse.json");

        Mockito.verify(accountService, Mockito.times(1)).changeAccountById(changeAccountDto, 1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenFindByIdNotExistedAccount_thenReturnError() throws Exception {

        Mockito.when(accountService.getAccountById(500)).thenThrow(new EntityNotFoundException("Account with id = 500 not found"));

        var actualResponse = mockMvc.perform(get("/auth/api/account/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/account/accountByIdNotFoundResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateAccountWithEmptyName_thenReturnValidationErrorResponse() throws Exception {
        AddAccountDto newAccount = new AddAccountDto(
                null,
                AccountType.DEPOSIT,
                AccountStatus.ACTIVE,
                0.0,
                CurrencyCode.CNY,
                1,
                Timestamp.valueOf(LocalDateTime.now()));

        var actualResponse = mockMvc.perform(post("/auth/api/account/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAccount)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/account/createAccountWithErrorsResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }
}
