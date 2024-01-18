package com.example.bank.repositories;

import com.example.bank.entity.Agreement;
import com.example.bank.entity.enums.AgreementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    List<Agreement> findAgreementsByStatus(AgreementStatus status);
}
