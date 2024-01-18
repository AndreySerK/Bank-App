package com.example.bank.controller;

import com.example.bank.TestUtils;
import com.example.bank.dto.manager.ManagerDto;
import com.example.bank.entity.enums.ManagerStatus;
import com.example.bank.service.ManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.createManagerDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    ManagerService managerService;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getAllTest_whenFindAll_thenReturnAllManagers() throws Exception {

        List<ManagerDto> managerDtoList = new ArrayList<>();
        managerDtoList.add(createManagerDto(1L));
        managerDtoList.add(createManagerDto(2L));


        Mockito.when(managerService.getAllManagers()).thenReturn(managerDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/managers/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/manager/getAllManagersResponse.json");

        Mockito.verify(managerService, Mockito.times(1)).getAllManagers();

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenGetManagerById_thenReturnManagerById() throws Exception {

        ManagerDto managerDto = createManagerDto(1L);

        Mockito.when(managerService.getManagerById(1)).thenReturn(managerDto);

        String actualResponse = mockMvc.perform(get("/auth/api/manager/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/manager/getManagerByIdResponse.json");

        Mockito.verify(managerService, Mockito.times(1)).getManagerById(1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByStatusTest_whenGetManagerByStatus_thenReturnManagerByStatus() throws Exception {

        List<ManagerDto> managerDtoList = new ArrayList<>();
        managerDtoList.add(createManagerDto(1L));
        managerDtoList.add(createManagerDto(2L));


        Mockito.when(managerService.getManagersByStatus(ManagerStatus.WORKING)).thenReturn(managerDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/managers/WORKING"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/manager/getManagersByStatusResponse.json");

        Mockito.verify(managerService, Mockito.times(1)).getManagersByStatus(ManagerStatus.WORKING);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateManager_thenReturnNewManager() throws Exception {

        ManagerDto managerDto = createManagerDto(1L);

        Mockito.when(managerService.createManager(managerDto)).thenReturn(managerDto);

        String actualResponse = mockMvc.perform(post("/auth/api/manager/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(managerDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/manager/createManagerResponse.json");

        Mockito.verify(managerService, Mockito.times(1)).createManager(managerDto);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void changeByIdTest_whenChangeAccount_thenReturnUpdatedAccount() throws Exception {

        ManagerDto changeManagerDto = new ManagerDto(
                "Mark",
                "Smith",
                ManagerStatus.FIRED
        );
        ManagerDto updatedManager = createManagerDto(1L);
        updatedManager.setStatus(ManagerStatus.FIRED);

        Mockito.when(managerService.changeManagerById(changeManagerDto, 1)).thenReturn(updatedManager);

        String actualResponse = mockMvc.perform(put("/auth/api/manager/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeManagerDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/manager/changeManagerResponse.json");

        Mockito.verify(managerService, Mockito.times(1)).changeManagerById(changeManagerDto, 1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenFindByIdNotExistedAccount_thenReturnError() throws Exception {

        Mockito.when(managerService.getManagerById(500)).thenThrow(new EntityNotFoundException("Manager with id = 500 not found"));

        var actualResponse = mockMvc.perform(get("/auth/api/manager/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/manager/managerByIdNotFoundResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateManagerWithEmptyFirstName_thenReturnValidationErrorResponse() throws Exception {
        ManagerDto managerDto = createManagerDto(1L);
        managerDto.setFirstName("");

        var actualResponse = mockMvc.perform(post("/auth/api/manager/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(managerDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/manager/createManagerWithErrorsResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }
}
