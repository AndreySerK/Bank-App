package com.example.bank.service;


import com.example.bank.dto.manager.ManagerDto;
import com.example.bank.entity.Manager;
import com.example.bank.entity.enums.ManagerStatus;
import com.example.bank.mappers.manager.ManagerMapper;
import com.example.bank.repositories.ManagerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;


    public List<ManagerDto> getAllManagers() {
        return managerMapper.toDtoList(managerRepository.findAll());
    }

    public ManagerDto getManagerById(Integer id) {
        return managerMapper
                .toDto(managerRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Manager with id = " + id + " not found")));
    }

    @Transactional
    public ManagerDto createManager(@Valid ManagerDto dto) {

        Manager newManager = managerMapper.toEntity(dto);
        managerRepository.save(newManager);
        return managerMapper.toDto(newManager);
    }

    public List<ManagerDto> getManagersByStatus(ManagerStatus status) {
        List<ManagerDto> accountDTOList = managerMapper
                .toDtoList(managerRepository
                        .findManagersByStatus(status));

        if (accountDTOList.isEmpty()) {
            throw new NullPointerException("There are no Managers with such status!");
        }

        return accountDTOList;
    }

    @Transactional
    public ManagerDto changeManagerById(@Valid ManagerDto dto, Integer id) {
        Manager Manager = managerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Manager with id = " + id + " not found")
        );

        Manager.setStatus(dto.getStatus());
        Manager.setFirstName(dto.getFirstName());
        Manager.setLastName(dto.getLastName());

        managerRepository.save(Manager);
        return managerMapper.toDto(Manager);
    }
}
