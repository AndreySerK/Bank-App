package com.example.bank.response.client;

import com.example.bank.dto.account.AccountDto;
import com.example.bank.dto.client.ClientDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfClientsResponse {

    private final List<ClientDto> clients;
}
