package com.example.HRplatform.service;

import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.exceptions.CandidateNotFoundException;
import com.example.HRplatform.mappers.CandidateMapper;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.repository.CandidateRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static java.lang.String.format;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    private final CandidateSkillService candidateSkillService;

    public CandidateService(CandidateRepository candidateRepository, CandidateMapper candidateMapper, @Lazy CandidateSkillService candidateSkillService) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
        this.candidateSkillService = candidateSkillService;
    }

    @Transactional
    public Candidate create(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Transactional(readOnly = true)
    public Candidate getByUuid(final UUID uuid) {
        return candidateRepository.findByUuid(uuid)
                                  .orElseThrow(() -> new CandidateNotFoundException(format("Candidate with uuid: %s does not exist", uuid)));
    }

    @Transactional(readOnly = true)
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Transactional
    public Candidate delete(UUID uuid) {
        final Candidate candidate = getByUuid(uuid);
        candidateSkillService.deleteCandidateSkills(candidate);
        candidateRepository.delete(candidate);
        return candidate;
    }

    @Transactional
    public Candidate update(final CandidateDto candidateDto) {
        final Candidate candidate = getByUuid(candidateDto.getUuid());
        candidateMapper.updateFields(candidate, candidateDto);
        candidateSkillService.addSkillsForCandidate(candidate, candidateDto.getSkills());
        return candidate;
    }

    @Transactional(readOnly = true)
    public List<Candidate> findAllByName(final String name) {
        return candidateRepository.findAllByName(name);
    }

    @Transactional(readOnly = true)
    public List<Candidate> findAllBySkills(List<String> skillNames) {
        skillNames.replaceAll(String::toUpperCase);
        return candidateRepository.findAllBySkillNames(skillNames);
    }

}