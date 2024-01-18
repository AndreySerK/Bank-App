package com.example.bank.service;

import com.example.bank.dto.manager.ManagerDto;
import com.example.bank.entity.Manager;
import com.example.bank.entity.enums.ManagerStatus;
import com.example.bank.repositories.ManagerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bank.TestUtils.*;
import static com.example.bank.TestUtils.createManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class ManagerServiceTest {

    @MockBean
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerService managerService;


    @Test
    public void getAllManagersTest_whenFindAll_thenReturnAllManagers() throws Exception {

        List<Manager> managerList = new ArrayList<>();
        managerList.add(createManager(1L));
        managerList.add(createManager(2L));

        when(managerRepository.findAll()).thenReturn(managerList);

        List<ManagerDto> actualList =  managerService.getAllManagers();

        assertEquals(2, actualList.size());
    }

    @Test
    public void getManagerByIdTest_whenGetManagerById_thenReturnManagerById() throws Exception {

        Manager manager = createManager(1L);
        ManagerDto expectedManagerDto = createManagerDto(1L);

        when(managerRepository.findById(1)).thenReturn(Optional.of(manager));

        ManagerDto actualManagerDto =  managerService.getManagerById(1);
        assertEquals(expectedManagerDto, actualManagerDto);
    }

    @Test
    public void createManagerTest_whenCreateManager_thenReturnNewManager() throws Exception {

        Manager manager = createManager(1L);
        ManagerDto managerDto = createManagerDto(1L);

        when(managerRepository.save(any(Manager.class))).thenReturn(manager);
        ManagerDto actualManagerDto =  managerService.createManager(managerDto);

        assertNotNull(actualManagerDto);
    }

    @Test
    public void getManagerByStatusTest_whenGetManagerByStatus_thenReturnManagerByStatus() throws Exception {
        List<Manager> cLientList = new ArrayList<>();
        cLientList.add(createManager(1L));
        cLientList.add(createManager(2L));


        when(managerRepository.findManagersByStatus(ManagerStatus.WORKING)).thenReturn(cLientList);

        List<ManagerDto> managerDtoList =  managerService.getManagersByStatus(ManagerStatus.WORKING);

        assertEquals(2, managerDtoList.size());
    }

    @Test
    public void changeManagerByIdTest_whenChangeManager_thenReturnUpdatedManager() throws Exception {

        Manager manager = createManager(1L);

        ManagerDto changeManagerDto = new ManagerDto(
                "John",
                "Doe",
                ManagerStatus.FIRED
        );

        when(managerRepository.findById(any(Integer.class))).thenReturn(Optional.of(manager));
        when(managerRepository.save(any(Manager.class))).thenReturn(manager);

        ManagerDto updatedManagerDto = managerService.changeManagerById(changeManagerDto, 1);

        assertEquals(changeManagerDto.getStatus(), updatedManagerDto.getStatus());
    }

    @Test
    public void getManagerByIdTest_whenFindByIdNotExistedManager_thenReturnException() throws Exception {

        when(managerRepository.findById(500)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> managerService.getManagerById(500));
        verify(managerRepository, times(1)).findById(500);
    }

    @Test
    public void getManagerByStatusTest_whenFindByStatusNotExistedManagers_thenReturnException() throws Exception {

        when(managerRepository.findManagersByStatus(ManagerStatus.FIRED)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> managerService.getManagersByStatus(ManagerStatus.FIRED));
        verify(managerRepository, times(1)).findManagersByStatus(ManagerStatus.FIRED);
    }
}
