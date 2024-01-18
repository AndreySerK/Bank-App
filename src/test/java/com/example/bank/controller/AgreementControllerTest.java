package com.example.bank.controller;

import com.example.bank.TestUtils;
import com.example.bank.dto.agreement.AgreementDto;
import com.example.bank.dto.agreement.ChangeAgreementDto;
import com.example.bank.entity.enums.AgreementStatus;
import com.example.bank.service.AgreementService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.createAgreementDto;
import static com.example.bank.TestUtils.getChangedAgreementDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AgreementControllerTest  {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    AgreementService agreementService;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getAllTest_whenFindAll_thenReturnAllAgreements() throws Exception {

        List<AgreementDto> agreementDtoList = new ArrayList<>();
        agreementDtoList.add(createAgreementDto(1L));
        agreementDtoList.add(createAgreementDto(2L));


        Mockito.when(agreementService.getAllAgreements()).thenReturn(agreementDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/agreements/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/agreement/getAllAgreementsResponse.json");

        Mockito.verify(agreementService, Mockito.times(1)).getAllAgreements();

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenGetAgreementById_thenReturnAgreementById() throws Exception {

        AgreementDto agreementDto = createAgreementDto(1L);

        Mockito.when(agreementService.getAgreementById(1)).thenReturn(agreementDto);

        String actualResponse = mockMvc.perform(get("/auth/api/agreement/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/agreement/getAgreementByIdResponse.json");

        Mockito.verify(agreementService, Mockito.times(1)).getAgreementById(1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByStatusTest_whenGetAgreementByStatus_thenReturnAgreementByStatus() throws Exception {

        List<AgreementDto> agreementDtoList = new ArrayList<>();
        agreementDtoList.add(createAgreementDto(1L));
        agreementDtoList.add(createAgreementDto(2L));


        Mockito.when(agreementService.getAgreementsByStatus(AgreementStatus.ACTIVE)).thenReturn(agreementDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/agreements/ACTIVE"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/agreement/getAgreementsByStatusResponse.json");

        Mockito.verify(agreementService, Mockito.times(1)).getAgreementsByStatus(AgreementStatus.ACTIVE);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateAgreement_thenReturnNewAgreement() throws Exception {

        AgreementDto agreementDto = createAgreementDto(1L);

        Mockito.when(agreementService.createAgreement(agreementDto)).thenReturn(agreementDto);

        String actualResponse = mockMvc.perform(post("/auth/api/agreement/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agreementDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/agreement/createAgreementResponse.json");

        Mockito.verify(agreementService, Mockito.times(1)).createAgreement(agreementDto);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void changeByIdTest_whenChangeAgreement_thenReturnUpdatedAgreement() throws Exception {

        ChangeAgreementDto changeAgreementDto = new ChangeAgreementDto(
                AgreementStatus.CLOSED,
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(0.002)
        );

        AgreementDto updatedAgreementDto = getChangedAgreementDto(1L, changeAgreementDto);
        Mockito.when(agreementService.changeAgreementById(changeAgreementDto, 1)).thenReturn(updatedAgreementDto);

        String actualResponse = mockMvc.perform(put("/auth/api/agreement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeAgreementDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/agreement/changeAgreementResponse.json");

        Mockito.verify(agreementService, Mockito.times(1)).changeAgreementById(changeAgreementDto, 1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenFindByIdNotExistedAgreement_thenReturnError() throws Exception {

        Mockito.when(agreementService.getAgreementById(500)).thenThrow(new EntityNotFoundException("Agreement with id = 500 not found"));

        var actualResponse = mockMvc.perform(get("/auth/api/agreement/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/agreement/agreementByIdNotFoundResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateAgreementWithEmptyAccountId_thenReturnValidationErrorResponse() throws Exception {
        AgreementDto agreementDto = createAgreementDto(1L);
        agreementDto.setAccountId(null);

        var actualResponse = mockMvc.perform(post("/auth/api/agreement/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agreementDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/agreement/createAgreementWithErrorsResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }
}
