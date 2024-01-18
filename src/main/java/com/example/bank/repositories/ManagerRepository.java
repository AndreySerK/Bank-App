package com.example.bank.repositories;

import com.example.bank.entity.Manager;
import com.example.bank.entity.enums.ManagerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    List<Manager> findManagersByStatus(ManagerStatus status);
}
