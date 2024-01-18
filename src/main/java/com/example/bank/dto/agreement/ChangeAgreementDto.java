package com.example.bank.dto.agreement;

import com.example.bank.entity.enums.AgreementStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAgreementDto {

    @NotNull(message = "The field must not be empty")
    private AgreementStatus status;

    @NotNull(message = "The field must not be empty")
    private BigDecimal sum;

    @NotNull(message = "The field must not be empty")
    private BigDecimal interestRate;
}
