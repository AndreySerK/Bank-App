package com.example.bank.service;

import com.example.bank.TestUtils;
import com.example.bank.dto.account.AccountDto;
import com.example.bank.dto.account.AddAccountDto;
import com.example.bank.dto.account.ChangeAccountDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import com.example.bank.repositories.AccountRepository;
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
public class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;


    @Test
    public void getAllAccountsTest_whenFindAll_thenReturnAllAccounts() throws Exception {

        List<Account> accountList = new ArrayList<>();
        accountList.add(createAccount(1L, createClient(1L, createManager(1L))));
        accountList.add(createAccount(2L, createClient(2L, createManager(1L))));

        when(accountRepository.findAll()).thenReturn(accountList);

        List<AccountDto> actualList =  accountService.getAllAccounts();

        assertEquals(2, actualList.size());
    }

    @Test
    public void getAccountByIdTest_whenGetAccountById_thenReturnAccountById() throws Exception {

        Account account = createAccount(1L, createClient(1L, createManager(1L)));
        AccountDto expectedAccountDto = createAccountDto(1L, createClientDto(1L));

        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        AccountDto actualAccountDto =  accountService.getAccountById(1);
        assertEquals(expectedAccountDto, actualAccountDto);
    }

    @Test
    public void createAccountTest_whenCreateAccount_thenReturnNewAccount() throws Exception {

        Account account = createAccount(1L, createClient(1L, createManager(1L)));
        AddAccountDto addAccountDto = TestUtils.getAddAccountDto(1);

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto actualAccountDto =  accountService.createAccount(addAccountDto);

        assertNotNull(actualAccountDto);
    }

    @Test
    public void getAccountByStatusTest_whenGetAccountByStatus_thenReturnAccountByStatus() throws Exception {
        List<Account> accountList = new ArrayList<>();
        accountList.add(createAccount(1L, createClient(1L, createManager(1L))));
        accountList.add(createAccount(2L, createClient(2L, createManager(1L))));


        when(accountRepository.findAccountsByStatus(AccountStatus.ACTIVE)).thenReturn(accountList);

        List<AccountDto> accountDtoList =  accountService.getAccountsByStatus(AccountStatus.ACTIVE);

        assertEquals(2, accountDtoList.size());
    }

    @Test
    public void changeAccountByIdTest_whenChangeAccount_thenReturnUpdatedAccount() throws Exception {

        Account account = createAccount(1L, createClient(1L, createManager(1L)));

        ChangeAccountDto changeAccountDto = new ChangeAccountDto(
                "ChangedAccount",
                AccountStatus.INACTIVE,
                AccountType.DEPOSIT,
                CurrencyCode.CNY,
                0);

        when(accountRepository.findById(any(Integer.class))).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto updatedAccountDto = accountService.changeAccountById(changeAccountDto, 1);

        assertEquals(changeAccountDto.getName(), updatedAccountDto.getName());
    }

    @Test
    public void getAccountByIdTest_whenFindByIdNotExistedAccount_thenReturnException() throws Exception {

        when(accountRepository.findById(500)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountById(500));
        verify(accountRepository, times(1)).findById(500);
    }

    @Test
    public void getAccountsByStatusTest_whenFindByStatusNotExistedAccounts_thenReturnException() throws Exception {

        when(accountRepository.findAccountsByStatus(AccountStatus.CLOSED)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> accountService.getAccountsByStatus(AccountStatus.CLOSED));
        verify(accountRepository, times(1)).findAccountsByStatus(AccountStatus.CLOSED);
    }
}

