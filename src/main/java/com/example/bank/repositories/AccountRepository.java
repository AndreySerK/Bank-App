package com.example.bank.repositories;

import com.example.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository <Account, Integer> {

    Account getAccountById (Long id);
}