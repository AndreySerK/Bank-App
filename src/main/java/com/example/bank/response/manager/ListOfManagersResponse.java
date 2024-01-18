package com.example.bank.response.manager;

import com.example.bank.dto.manager.ManagerDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfManagersResponse {

    private final List<ManagerDto> managers;
}
