package com.example.bank.mapper;

import com.example.bank.dto.account.AccountDto;
import com.example.bank.dto.account.AddAccountDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import com.example.bank.mappers.account.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static com.example.bank.TestUtils.createClient;
import static com.example.bank.TestUtils.createManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AccountMapperTest {

    @Autowired
    AccountMapper accountMapper;

    Account account;
    AddAccountDto accountDto;

    @BeforeEach
    void setup() {
        account = new Account(
                1L,
                "MyAccount",
                AccountType.CURRENT,
                AccountStatus.ACTIVE,
                50,
                CurrencyCode.USD,
                Timestamp.valueOf("2024-02-01 00:00:00"),
                null,
                createClient(1L,createManager(1L)),
                null,
                null,
                null
        );
        accountDto = new AddAccountDto(
                "MyAccount",
                AccountType.CURRENT,
                AccountStatus.ACTIVE,
                50,
                CurrencyCode.USD,
                1,
                Timestamp.valueOf("2024-02-01 00:00:00")
        );
    }

    @Test
    public void toDtoTest_whenGetAccount_shouldReturnAccountDto () {

        AccountDto dto = accountMapper.toDto(account);

        assertEquals(dto.getName(), account.getName());
        assertEquals(dto.getType(), account.getType());
        assertEquals(dto.getBalance(), account.getBalance());
        assertEquals(dto.getStatus(), account.getStatus());
        assertEquals(dto.getCurrencyCode(), account.getCurrencyCode());
        assertEquals(dto.getCreatedAt(), account.getCreatedAt());
    }

    @Test
    public void toEntityTest_whenGetAccountDto_shouldReturnAccount () {

        Account entity = accountMapper.toEntity(accountDto);

        assertEquals(entity.getName(), accountDto.getName());
        assertEquals(entity.getType(), accountDto.getType());
        assertEquals(entity.getBalance(), accountDto.getBalance());
        assertEquals(entity.getStatus(), accountDto.getStatus());
        assertEquals(entity.getCurrencyCode(), accountDto.getCurrencyCode());
    }
}
