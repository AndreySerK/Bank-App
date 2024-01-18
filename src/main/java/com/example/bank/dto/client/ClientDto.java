package com.example.bank.dto.client;

import com.example.bank.entity.enums.ClientStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {


    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotBlank(message = "The field must not be blank")
    private String taxCode;

    @NotNull(message = "The field must not be empty")
    private ClientStatus status;

    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "The field must not be blank")
    @Digits(integer = 11, fraction = 0)
    private String phone;

    @NotBlank(message = "The field must not be blank")
    private String address;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @NotNull(message = "The field must not be empty")
    private Long managerId;
}
