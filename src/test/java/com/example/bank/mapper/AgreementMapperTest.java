package com.example.bank.mapper;

import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.entity.Agreement;
import com.example.bank.mappers.agreement.AgreementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bank.TestUtils.createAgreement;
import static com.example.bank.TestUtils.createAgreementDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AgreementMapperTest {

    @Autowired
    AgreementMapper agreementMapper;

    Agreement agreement;
    AgreementDto agreementDto;

    @BeforeEach
    void setup() {
        agreement = createAgreement(1L);
        agreementDto = createAgreementDto(1L);
    }

    @Test
    public void toDtoTest_whenGetAgreement_shouldReturnAgreementDto () {

        AgreementDto actualAgreementDto = agreementMapper.toDto(agreement);

        assertEquals(agreementDto.getStatus(), actualAgreementDto.getStatus());
        assertEquals(agreementDto.getSum(), actualAgreementDto.getSum());
        assertEquals(agreementDto.getInterestRate(), actualAgreementDto.getInterestRate());
        assertEquals(agreementDto.getCreatedAt(), actualAgreementDto.getCreatedAt());
        assertEquals(agreementDto.getUpdatedAt(), actualAgreementDto.getUpdatedAt());
        assertEquals(agreementDto.getAccountId(), actualAgreementDto.getAccountId());
        assertEquals(agreementDto.getProductId(), actualAgreementDto.getProductId());
    }

    @Test
    public void toEntityTest_whenGetAgreementDto_shouldReturnAgreement () {

        Agreement actualAgreement = agreementMapper.toEntity(agreementDto);


        assertEquals(agreement.getStatus(), actualAgreement.getStatus());
        assertEquals(agreement.getSum(), actualAgreement.getSum());
        assertEquals(agreement.getInterestRate(), actualAgreement.getInterestRate());
    }
}
