package com.example.bank.mapper;

import com.example.bank.dto.manager.ManagerDto;
import com.example.bank.entity.Manager;
import com.example.bank.mappers.manager.ManagerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bank.TestUtils.createManager;
import static com.example.bank.TestUtils.createManagerDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ManagerMapperTest {

    @Autowired
    ManagerMapper managerMapper;

    Manager manager;
    ManagerDto managerDto;

    @BeforeEach
    void setup() {
        manager = createManager(1L);
        managerDto = createManagerDto(1L);
    }

    @Test
    public void toDtoTest_whenGetManager_shouldReturnManagerDto () {

        ManagerDto actualManagerDto = managerMapper.toDto(manager);

        assertEquals(managerDto.getStatus(), actualManagerDto.getStatus());
        assertEquals(managerDto.getFirstName(), actualManagerDto.getFirstName());
        assertEquals(managerDto.getLastName(), actualManagerDto.getLastName());
    }

    @Test
    public void toEntityTest_whenGetManagerDto_shouldReturnManager () {

        Manager actualManager = managerMapper.toEntity(managerDto);

        assertEquals(manager.getFirstName(), actualManager.getFirstName());
        assertEquals(manager.getLastName(), actualManager.getLastName());
        assertEquals(manager.getStatus(), actualManager.getStatus());
    }
}
