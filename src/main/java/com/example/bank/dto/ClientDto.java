package com.example.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}