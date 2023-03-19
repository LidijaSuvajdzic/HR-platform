package com.example.HRplatform.service;

import com.example.HRplatform.dto.RemoveSkillRequestDto;
import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.exceptions.EntityNotFoundException;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.CandidateSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CandidateSkillService {

    private final CandidateSkillRepository candidateSkillRepository;
    private final SkillService skillService;
    private final CandidateService candidateService;

    @Transactional
    public void save(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }

    @Transactional
    public void addSkillsForCandidate(final Candidate candidate, final List<SkillDto> skills) {
        skills.forEach(skillDto -> {
            final Skill skill = skillService.getByName(skillDto.getName());
            final CandidateSkill candidateSkill = CandidateSkill.builder()
                    .candidate(candidate)
                    .skill(skill).build();
            save(candidateSkill);
        });
    }

    @Transactional(readOnly = true)
    public CandidateSkill getByCandidateAndSkill(final Candidate candidate, final Skill skill) {
       return candidateSkillRepository.findByCandidateAndSkill(candidate, skill)
               .orElseThrow(() -> new EntityNotFoundException(format("There is no candidate: %s with skill: %s .", candidate.getName(), skill.getName())));
    }

    @Transactional
    public void removeSkillFromCandidate(final RemoveSkillRequestDto removeSkillRequestDto) {
        final Candidate candidate = candidateService.getByUuid(removeSkillRequestDto.getCandidateUuid());
        final Skill skill = skillService.getByName(removeSkillRequestDto.getSkillName().toUpperCase());
        final CandidateSkill candidateSkill = getByCandidateAndSkill(candidate, skill);
        candidateSkillRepository.delete(candidateSkill);
    }

    @Transactional
    public void deleteCandidateSkills(final Candidate candidate) {
        List<CandidateSkill> candidateSKills = candidateSkillRepository.findAllByCandidate(candidate);
        candidateSKills.forEach(candidateSkillRepository::delete);
    }

    @Transactional
    public void deleteCandidateSkills(final Skill skill) {
        List<CandidateSkill> candidateSKills = candidateSkillRepository.findAllBySkill(skill);
        candidateSKills.forEach(candidateSkillRepository::delete);
    }
}
