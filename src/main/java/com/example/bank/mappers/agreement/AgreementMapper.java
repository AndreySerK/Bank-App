package com.example.bank.mappers.agreement;

import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.entity.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

@Mapper(componentModel = "spring", imports = Timestamp.class)
public interface AgreementMapper {

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Agreement toEntity(AgreementDto agreementDto);

    @Mapping(source = "agreement.account.id", target = "accountId")
    @Mapping(source = "agreement.product.id", target = "productId")
    AgreementDto toDto(Agreement agreement);
}
