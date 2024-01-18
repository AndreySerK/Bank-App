package com.example.bank.service;

import com.example.bank.dto.agreement.ChangeAgreementDto;
import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.entity.Agreement;
import com.example.bank.entity.enums.AgreementStatus;
import com.example.bank.repositories.AgreementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AgreementServiceTest {

    @MockBean
    private AgreementRepository agreementRepository;

    @Autowired
    private AgreementService agreementService;


    @Test
    public void getAllAgreementsTest_whenFindAll_thenReturnAllAgreements() throws Exception {

        List<Agreement> agreementList = new ArrayList<>();
        agreementList.add(createAgreement(1L));
        agreementList.add(createAgreement(2L));

        when(agreementRepository.findAll()).thenReturn(agreementList);

        List<AgreementDto> actualList =  agreementService.getAllAgreements();

        assertEquals(2, actualList.size());
    }

    @Test
    public void getAgreementByIdTest_whenGetAgreementById_thenReturnAgreementById() throws Exception {

        Agreement agreement = createAgreement(1L);
        AgreementDto expectedAgreementDto = createAgreementDto(1L);

        when(agreementRepository.findById(1)).thenReturn(Optional.of(agreement));

        AgreementDto actualAgreementDto =  agreementService.getAgreementById(1);
        assertEquals(expectedAgreementDto, actualAgreementDto);
    }

    @Test
    public void createAgreementTest_whenCreateAgreement_thenReturnNewAgreement() throws Exception {

        Agreement agreement = createAgreement(1L);
        AgreementDto agreementDto = createAgreementDto(1L);

        when(agreementRepository.save(any(Agreement.class))).thenReturn(agreement);
        AgreementDto actualAgreementDto =  agreementService.createAgreement(agreementDto);

        assertNotNull(actualAgreementDto);
    }

    @Test
    public void getAgreementByStatusTest_whenGetAgreementByStatus_thenReturnAgreementByStatus() throws Exception {
        List<Agreement> cLientList = new ArrayList<>();
        cLientList.add(createAgreement(1L));
        cLientList.add(createAgreement(2L));


        when(agreementRepository.findAgreementsByStatus(AgreementStatus.ACTIVE)).thenReturn(cLientList);

        List<AgreementDto> agreementDtoList =  agreementService.getAgreementsByStatus(AgreementStatus.ACTIVE);

        assertEquals(2, agreementDtoList.size());
    }

    @Test
    public void changeAgreementByIdTest_whenChangeAgreement_thenReturnUpdatedAgreement() throws Exception {

        Agreement agreement = createAgreement(1L);

        ChangeAgreementDto changeAgreementDto = new ChangeAgreementDto(
                AgreementStatus.CLOSED,
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(0.002)
        );

        when(agreementRepository.findById(any(Integer.class))).thenReturn(Optional.of(agreement));
        when(agreementRepository.save(any(Agreement.class))).thenReturn(agreement);

        AgreementDto updatedAgreementDto = agreementService.changeAgreementById(changeAgreementDto, 1);

        assertEquals(changeAgreementDto.getStatus(), updatedAgreementDto.getStatus());
    }

    @Test
    public void getAgreementByIdTest_whenFindByIdNotExistedAgreement_thenReturnException() throws Exception {

        when(agreementRepository.findById(500)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> agreementService.getAgreementById(500));
        verify(agreementRepository, times(1)).findById(500);
    }

    @Test
    public void getAgreementByStatusTest_whenFindByStatusNotExistedAgreements_thenReturnException() throws Exception {

        when(agreementRepository.findAgreementsByStatus(AgreementStatus.INACTIVE)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> agreementService.getAgreementsByStatus(AgreementStatus.INACTIVE));
        verify(agreementRepository, times(1)).findAgreementsByStatus(AgreementStatus.INACTIVE);
    }
}