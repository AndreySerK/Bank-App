package com.example.bank.response.agreement;

import com.example.bank.dto.agreement.AgreementDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfAgreementsResponse {

    private final List<AgreementDto> agreements;
}
