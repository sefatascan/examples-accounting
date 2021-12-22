package com.sefa.accounting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sefa.accounting.dto.request.SpecialistRequest;
import com.sefa.accounting.dto.response.SpecialistDetailResponse;
import com.sefa.accounting.service.SpecialistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = SpecialistController.class)
class SpecialistControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SpecialistService specialistService;

    @Test
    void createSpecialist() throws Exception {
        SpecialistRequest specialistRequest = new SpecialistRequest();
        specialistRequest.setFirstName("John");
        specialistRequest.setLastName("Doe");
        specialistRequest.setEmail("john.doe@gmail.com");

        SpecialistDetailResponse specialistDetailResponse = new SpecialistDetailResponse();
        specialistDetailResponse.setFirstName("John");
        specialistDetailResponse.setLastName("Doe");
        specialistDetailResponse.setEmail("john.doe@gmail.com");


        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(specialistRequest);

        RequestBuilder builder = MockMvcRequestBuilders
                .post("/v1/specialist")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(specialistService.createSpecialist(specialistRequest))
                .thenReturn(specialistDetailResponse);

        MvcResult result = mvc.perform(builder)
                .andExpect(status().isOk())
                .andReturn();

        SpecialistDetailResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                SpecialistDetailResponse.class);

        assertThat(response).hasFieldOrPropertyWithValue("firstName", "John");
        assertThat(response).hasFieldOrPropertyWithValue("lastName", "Doe");
        assertThat(response).hasFieldOrPropertyWithValue("email", "john.doe@gmail.com");

    }
}