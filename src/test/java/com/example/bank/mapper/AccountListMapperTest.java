package com.example.bank.mapper;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.bank.dto.account.AccountDto;
import com.example.bank.entity.Account;
import com.example.bank.mappers.account.AccountListMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AccountListMapperTest {

    @Autowired
    AccountListMapper accountListMapper;

    @Test
    public void toDtoListTest_whenGetAccountList_shouldReturnAccountDtoList () {
        List<Account> accountList = new ArrayList<>();
        accountList.add(createAccount(1L, createClient(1L, createManager(1L))));

        List<AccountDto> expectedAccountDtoList = new ArrayList<>();
        expectedAccountDtoList.add(createAccountDto(1L, createClientInAccountDto(1L)));

        List<AccountDto> actualAccountDtoList = accountListMapper.toDtoList(accountList);

        assertEquals(expectedAccountDtoList, actualAccountDtoList);
    }
}
