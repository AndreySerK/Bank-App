package com.example.bank.repository;

import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findAccountByIdTest_whenFoundAccountById_thenReturnAccountById() throws Exception {

        Account account = createAccount(1L, createClient(1L, createManager(1L)));

        accountRepository.save(account);

        Account accountDB = accountRepository.findById(1).get();

        assertThat(accountDB).isNotNull();
        assertEquals(account.getName(), accountDB.getName());
    }

    @Test
    public void findAccountsByStatusTest_whenFoundAccountsByStatus_thenReturnAccountsByStatus() throws Exception {

        List<Account> actualList = accountRepository.findAccountsByStatus(AccountStatus.ACTIVE);

        assertThat(actualList).isNotNull();
        assertEquals(2, actualList.size());
    }
}
