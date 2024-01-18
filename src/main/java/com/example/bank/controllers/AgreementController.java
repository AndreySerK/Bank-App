package com.example.bank.controllers;

import com.example.bank.dto.agreement.ChangeAgreementDto;
import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.entity.enums.AgreementStatus;
import com.example.bank.response.agreement.ListOfAgreementsResponse;
import com.example.bank.service.AgreementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AgreementController{

    private final AgreementService agreementService;

    @GetMapping("/api/agreements/all")
    @ResponseStatus(HttpStatus.OK)
    public ListOfAgreementsResponse getAll () {
        return new ListOfAgreementsResponse(agreementService.getAllAgreements());
    }

    @GetMapping ("/api/agreement/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AgreementDto getById (@PathVariable Integer id) {
        return agreementService.getAgreementById(id);
    }

    @PostMapping("/api/agreement/post")
    @ResponseStatus(HttpStatus.OK)
    public AgreementDto create (@RequestBody @Valid AgreementDto agreement) {
        return agreementService.createAgreement(agreement);
    }

    @GetMapping ("/api/agreements/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ListOfAgreementsResponse getByStatus (@PathVariable AgreementStatus status) {
        return new ListOfAgreementsResponse(agreementService.getAgreementsByStatus(status));
    }

    @PutMapping ("/api/agreement/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AgreementDto changeById (@RequestBody ChangeAgreementDto agreement,
                                 @PathVariable Integer id) {
        return agreementService.changeAgreementById(agreement, id);
    }
}

