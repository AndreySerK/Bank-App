package com.example.bank.repositories;

import com.example.bank.entity.Client;
import com.example.bank.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findClientsByStatus (ClientStatus status);
}
