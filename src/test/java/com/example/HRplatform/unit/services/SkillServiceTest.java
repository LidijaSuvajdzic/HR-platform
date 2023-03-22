package com.example.HRplatform.unit.services;

import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.SkillRepository;
import com.example.HRplatform.service.SkillService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    @Test
    public void test_findAllByCandidate() {
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
        List<Skill> skills = List.of(skill1);

        when(skillRepository.findAllByCandidateId(candidate.getId())).thenReturn(skills);

        List<Skill> result = skillService.findAllByCandidate(candidate);

        assertEquals(skills.size(), result.size());
        assertEquals(skills.get(0).getName(), result.get(0).getName());
        verify(skillRepository, times(1)).findAllByCandidateId(candidate.getId());


    }
}
