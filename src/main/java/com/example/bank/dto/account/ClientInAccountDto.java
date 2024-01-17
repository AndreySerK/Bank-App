package com.example.bank.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInAccountDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
