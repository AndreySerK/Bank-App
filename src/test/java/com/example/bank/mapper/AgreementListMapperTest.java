package com.example.bank.mapper;

import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.entity.Agreement;
import com.example.bank.mappers.agreement.AgreementListMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AgreementListMapperTest {

    @Autowired
    AgreementListMapper agreementListMapper;

    @Test
    public void toDtoListTest_whenGetAgreementList_shouldReturnAgreementDtoList () {
        List<Agreement> agreementList = new ArrayList<>();
        agreementList.add(createAgreement(1L));

        List<AgreementDto> expectedAgreementDtoList = new ArrayList<>();
        expectedAgreementDtoList.add(createAgreementDto(1L));

        List<AgreementDto> actualAgreementDtoList = agreementListMapper.toDtoList(agreementList);

        assertEquals(expectedAgreementDtoList, actualAgreementDtoList);
    }
}
