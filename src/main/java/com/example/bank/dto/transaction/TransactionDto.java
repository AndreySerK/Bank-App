package com.example.bank.dto.transaction;

import com.example.bank.entity.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    @NotNull(message = "The field must not be empty")
    private TransactionType type;

    @NotNull(message = "The field must not be empty")
    private double amount;

    private String description;

    private Timestamp createdAt;

    @NotNull(message = "The field must not be empty")
    private Long debitAccountId;

    @NotNull(message = "The field must not be empty")
    private Long creditAccountId;
}
