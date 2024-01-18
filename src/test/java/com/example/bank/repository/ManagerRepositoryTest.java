package com.example.bank.repository;

import com.example.bank.entity.Manager;
import com.example.bank.entity.enums.ManagerStatus;
import com.example.bank.repositories.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.bank.TestUtils.createManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ManagerRepositoryTest {

    @Autowired
    ManagerRepository managerRepository;

    @Test
    public void findManagerByIdTest_whenFoundManagerById_thenReturnManagerById() throws Exception {

        Manager manager = createManager(1L);

        managerRepository.save(manager);

        Manager managerDB = managerRepository.findById(1).get();

        assertThat(managerDB).isNotNull();
        assertEquals(manager.getFirstName(), managerDB.getFirstName());
    }

    @Test
    public void findManagersByStatusTest_whenFoundManagersByStatus_thenReturnManagersByStatus() throws Exception {

        List<Manager> actualList = managerRepository.findManagersByStatus(ManagerStatus.WORKING);

        assertThat(actualList).isNotNull();
        assertEquals(1, actualList.size());
    }

    @Test
    public void findAllManagersTest_whenFoundAllManagers_thenReturnAllManagers() throws Exception {

        List<Manager> actualList = managerRepository.findAll();

        assertThat(actualList).isNotNull();
        assertEquals(1, actualList.size());
    }
}
