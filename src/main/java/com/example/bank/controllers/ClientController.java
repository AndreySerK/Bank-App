package com.example.bank.controllers;

import com.example.bank.dto.client.ChangeClientDto;
import com.example.bank.dto.client.ClientDto;
import com.example.bank.entity.enums.ClientStatus;
import com.example.bank.response.client.ListOfClientsResponse;
import com.example.bank.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ClientController{

    private final ClientService clientService;

    @GetMapping("/api/clients/all")
    @ResponseStatus(HttpStatus.OK)
    public ListOfClientsResponse getAll () {
        return new ListOfClientsResponse(clientService.getAllClients());
    }

    @GetMapping ("/api/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getById (@PathVariable Integer id) {
        return clientService.getClientById(id);
    }

    @PostMapping("/api/client/post")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto create (@RequestBody @Valid ClientDto client) {
        return clientService.createClient(client);
    }

    @GetMapping ("/api/clients/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ListOfClientsResponse getByStatus (@PathVariable ClientStatus status) {
        return new ListOfClientsResponse(clientService.getClientsByStatus(status));
    }

    @PutMapping ("/api/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto changeById (@RequestBody ChangeClientDto client,
                                  @PathVariable Integer id) {
        return clientService.changeClientById(client, id);
    }
}
