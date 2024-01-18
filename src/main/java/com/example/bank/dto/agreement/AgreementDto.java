package com.example.bank.dto.agreement;

import com.example.bank.entity.enums.AgreementStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgreementDto {

    @NotNull(message = "The field must not be empty")
    private Long accountId;

    @NotNull(message = "The field must not be empty")
    private Long productId;

    @NotNull(message = "The field must not be empty")
    private AgreementStatus status;

    @NotNull(message = "The field must not be empty")
    private BigDecimal sum;

    @NotNull(message = "The field must not be empty")
    private BigDecimal interestRate;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
