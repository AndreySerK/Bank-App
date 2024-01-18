package com.example.bank.mappers.manager;

import com.example.bank.dto.manager.ManagerDto;
import com.example.bank.entity.Manager;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManagerMapper {

    Manager toEntity(ManagerDto managerDto);

    ManagerDto toDto(Manager manager);

    List<ManagerDto> toDtoList(List<Manager> managerList);
}