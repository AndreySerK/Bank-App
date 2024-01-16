package com.example.bank.controller;

import com.example.bank.StringTestUtils;
import com.example.bank.dto.AccountDto;
import com.example.bank.response.accounts.GetAllAccountsResponse;
import com.example.bank.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends AbstractTestController {

    @MockBean
    AccountService accountService;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void whenFindAll_thenReturnAllAccounts () throws Exception {

//        List<Account> accounts = new ArrayList<>();
//        Manager manager = createManager(1L);
//        Client client = createClient(1L, manager);
//        accounts.add(createAccount(1L, client));

        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(createAccountDto(1L, createClientDto(1L)));
        accountDtoList.add(createAccountDto(2L, createClientDto(2L)));

        GetAllAccountsResponse response = createResponse(accountDtoList);

        Mockito.when(accountService.getAllAccounts()).thenReturn(accountDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/accounts/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("/response/getAllAccountsResponse.json");

        Mockito.verify(accountService, Mockito.times(1)).getAllAccounts();

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }
}
