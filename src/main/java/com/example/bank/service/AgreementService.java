package com.example.bank.service;

import com.example.bank.dto.agreement.ChangeAgreementDto;
import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.entity.Agreement;
import com.example.bank.entity.enums.AgreementStatus;
import com.example.bank.mappers.agreement.AgreementListMapper;
import com.example.bank.mappers.agreement.AgreementMapper;
import com.example.bank.repositories.AgreementRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class AgreementService {

    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;
    private final AgreementListMapper agreementListMapper;


    public List<AgreementDto> getAllAgreements() {
        return agreementListMapper.toDtoList(agreementRepository.findAll());
    }

    public AgreementDto getAgreementById(Integer id) {
        return agreementMapper
                .toDto(agreementRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Agreement with id = " + id + " not found")));
    }

    @Transactional
    public AgreementDto createAgreement(@Valid AgreementDto dto) {

        Agreement newAgreement = agreementMapper.toEntity(dto);
        agreementRepository.save(newAgreement);
        return agreementMapper.toDto(newAgreement);
    }

    public List<AgreementDto> getAgreementsByStatus(AgreementStatus status) {
        List<AgreementDto> agreementDTOList = agreementListMapper
                .toDtoList(agreementRepository
                        .findAgreementsByStatus(status));

        if (agreementDTOList.isEmpty()) {
            throw new NullPointerException("There are no agreements with such status!");
        }

        return agreementDTOList;
    }

    @Transactional
    public AgreementDto changeAgreementById(@Valid ChangeAgreementDto dto, Integer id) {
        Agreement agreement = agreementRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Agreement with id = " + id + " not found")
        );

        agreement.setSum(dto.getSum());
        agreement.setInterestRate(dto.getInterestRate());
        agreement.setStatus(dto.getStatus());

        agreementRepository.save(agreement);
        return agreementMapper.toDto(agreement);
    }
}
