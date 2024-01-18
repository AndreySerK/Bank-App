package com.example.bank.mappers.agreement;

import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.entity.Agreement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AgreementMapper.class)
public interface AgreementListMapper {

    List<AgreementDto> toDtoList(List<Agreement> agreementList);
}
