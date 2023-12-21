package com.example.bank.repositories;

import com.example.bank.entity.Account;
import com.example.bank.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository <Account, Integer> {

    Account getAccountById (Long id);
    List<Account> findAccountsByStatus (AccountStatus status);
}
