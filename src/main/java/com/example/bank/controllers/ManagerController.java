package com.example.bank.controllers;

import com.example.bank.dto.manager.ManagerDto;
import com.example.bank.entity.enums.ManagerStatus;
import com.example.bank.response.manager.ListOfManagersResponse;
import com.example.bank.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/api/managers/all")
    @ResponseStatus(HttpStatus.OK)
    public ListOfManagersResponse getAll () {
        return new ListOfManagersResponse(managerService.getAllManagers());
    }

    @GetMapping ("/api/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ManagerDto getById (@PathVariable Integer id) {
        return managerService.getManagerById(id);
    }

    @PostMapping("/api/manager/post")
    @ResponseStatus(HttpStatus.OK)
    public ManagerDto create (@RequestBody @Valid ManagerDto manager) {
        return managerService.createManager(manager);
    }

    @GetMapping ("/api/managers/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ListOfManagersResponse getByStatus (@PathVariable ManagerStatus status) {
        return new ListOfManagersResponse(managerService.getManagersByStatus(status));
    }

    @PutMapping ("/api/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ManagerDto changeById (@RequestBody ManagerDto manager,
                                 @PathVariable Integer id) {
        return managerService.changeManagerById(manager, id);
    }
}
