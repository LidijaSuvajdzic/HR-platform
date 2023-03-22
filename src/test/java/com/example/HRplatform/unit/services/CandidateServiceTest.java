package com.example.HRplatform.unit.services;

import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.CandidateRepository;
import com.example.HRplatform.service.CandidateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    public void test_findAllBySkills() {
        List<String> skillNames = new ArrayList<>();
        skillNames.add("Java developer");
        skillNames.add("Android developer");
        Candidate candidate = Candidate.builder()
                .id(1L)
                .email("test@test")
                .uuid(UUID.randomUUID())
                .name("test_name")
                .dateOfBirth(LocalDate.now())
                .phoneNumber("1342342").build();
        Skill skill1 = Skill.builder().id(1L).name("Java developer").build();
        Skill skill2 = Skill.builder().id(2L).name("Android developer").build();
        CandidateSkill candidateSkill = CandidateSkill.builder().candidate(candidate).skill(skill1).build();
        List<Candidate> candidates = List.of(candidate);
        when(candidateRepository.findAllBySkillNames(skillNames)).thenReturn(candidates);

        List<Candidate> result = candidateService.findAllBySkills(skillNames);

        assertEquals(candidates.size(), result.size());
        assertEquals(candidates.get(0).getEmail(), result.get(0).getEmail());
        verify(candidateRepository, times(1)).findAllBySkillNames(skillNames);

    }
}
