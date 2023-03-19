package com.example.HRplatform.service;
import com.example.HRplatform.exceptions.SkillExistsException;
import com.example.HRplatform.exceptions.SkillNotFoundException;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.SkillRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class  SkillService {

    private final SkillRepository skillRepository;
    private final CandidateSkillService candidateSkillService;

    public SkillService(SkillRepository skillRepository, @Lazy CandidateSkillService candidateSkillService) {
        this.skillRepository = skillRepository;
        this.candidateSkillService = candidateSkillService;
    }

    @Transactional
    public Skill save(final Skill skill) {
        if (findByName(skill.getName()).isPresent()) {
            throw new SkillExistsException(skill.getName());
        }
        return skillRepository.save(skill);
    }

    @Transactional(readOnly = true)
    public Optional<Skill> findByName(final String name) {
        return skillRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Skill getByName(final String name) {
        return findByName(name)
                .orElseThrow(() -> new SkillNotFoundException(format("Skill with name: %s does not exist", name)));
    }

    @Transactional
    public Skill delete(final String name) {
        final Skill skill = getByName(name);
        candidateSkillService.deleteCandidateSkills(skill);
        skillRepository.delete(skill);
        return skill;
    }

    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Skill> findAllByCandidate(final Candidate candidate) {
        return skillRepository.findAllByCandidateId(candidate.getId());
    }


}
