package com.example.HRplatform.mappers;

import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.dto.CreateCandidateDto;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CandidateMapper {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    public Candidate map(final CandidateDto candidateDto) {
        return Candidate.builder()
                .uuid(candidateDto.getUuid())
                .name(candidateDto.getName())
                .email(candidateDto.getEmail())
                .phoneNumber(candidateDto.getPhoneNumber())
                .dateOfBirth(candidateDto.getDateOfBirth())
                .build();
    }

    public CandidateDto map(final Candidate candidate) {
        return CandidateDto.builder()
                .uuid(candidate.getUuid())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .phoneNumber(candidate.getPhoneNumber())
                .dateOfBirth(candidate.getDateOfBirth())
                .skills(skillMapper.map(skillService.findAllByCandidate(candidate)))
                .build();
    }

    public Candidate map(final CreateCandidateDto createCandidateDto) {
        return Candidate.builder()
                .name(createCandidateDto.getName())
                .email(createCandidateDto.getEmail())
                .phoneNumber(createCandidateDto.getPhoneNumber())
                .dateOfBirth(createCandidateDto.getDateOfBirth())
                .build();
    }

    public void updateFields(final Candidate candidate, final CandidateDto candidateDto) {
        candidate.setName(candidateDto.getName());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        candidate.setDateOfBirth(candidateDto.getDateOfBirth());
    }
}
