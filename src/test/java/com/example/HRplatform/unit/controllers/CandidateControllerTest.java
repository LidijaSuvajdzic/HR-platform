package com.example.HRplatform.unit.controllers;

import com.example.HRplatform.controller.CandidateController;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.mappers.CandidateMapper;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.service.CandidateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(CandidateController.class)
@AutoConfigureMockMvc
public class CandidateControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidateService candidateService;

    @MockBean
    private CandidateMapper candidateMapper;

    @Test
    public void test_findAllBySkills() throws Exception {
        List<String> skillNames = new ArrayList<>();
        skillNames.add("Java developer");
        skillNames.add("Android developer");
        UUID candidateUUID = UUID.randomUUID();
        Candidate candidate = Candidate.builder()
                .id(1L)
                .email("test@test")
                .uuid(candidateUUID)
                .name("test_name")
                .dateOfBirth(LocalDate.now())
                .phoneNumber("1342342").build();
        Skill skill1 = Skill.builder().id(1L).name("Java developer").build();
        Skill skill2 = Skill.builder().id(2L).name("Android developer").build();
        CandidateSkill candidateSkill = CandidateSkill.builder().candidate(candidate).skill(skill1).build();
        List<Candidate> candidates = List.of(candidate);

        when(candidateService.findAllBySkills(skillNames)).thenReturn(candidates);
        candidates.forEach(c -> {
            CandidateDto candidateDto = CandidateDto.builder()
                    .email("test@test")
                    .uuid(candidateUUID)
                    .name("test_name")
                    .dateOfBirth(LocalDate.now())
                    .phoneNumber("1342342")
                    .build();
            when(candidateMapper.map(c)).thenReturn(candidateDto);
        });

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/candidates/bySkills/")
                .content(new ObjectMapper().writeValueAsString(skillNames))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();

        List<CandidateDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(candidates.size(), response.size());
        assertEquals(candidates.get(0).getEmail(), response.get(0).getEmail());
        assertEquals(candidates.get(0).getDateOfBirth(), response.get(0).getDateOfBirth());
        assertEquals(candidates.get(0).getPhoneNumber(), response.get(0).getPhoneNumber());
        verify(candidateService,times(1)).findAllBySkills(skillNames);
    }
}
