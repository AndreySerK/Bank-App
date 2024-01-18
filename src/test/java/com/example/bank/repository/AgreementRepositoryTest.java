package com.example.bank.repository;

import com.example.bank.entity.Agreement;
import com.example.bank.entity.enums.AgreementStatus;
import com.example.bank.repositories.AgreementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AgreementRepositoryTest {

    @Autowired
    AgreementRepository agreementRepository;

    @Test
    public void findAgreementByIdTest_whenFoundAgreementById_thenReturnAgreementById() throws Exception {

        Agreement agreementDB = agreementRepository.findById(1).get();

        assertThat(agreementDB).isNotNull();
        assertEquals(agreementDB.getInterestRate(), BigDecimal.valueOf(0.03));
    }

    @Test
    public void findAgreementsByStatusTest_whenFoundAgreementsByStatus_thenReturnAgreementsByStatus() throws Exception {

        List<Agreement> actualList = agreementRepository.findAgreementsByStatus(AgreementStatus.ACTIVE);

        assertThat(actualList).isNotNull();
        assertEquals(1, actualList.size());
    }

    @Test
    public void findAllAgreementsTest_whenFoundAllAgreements_thenReturnAllAgreements() throws Exception {

        List<Agreement> actualList = agreementRepository.findAll();

        assertThat(actualList).isNotNull();
        assertEquals(1, actualList.size());
    }
}
