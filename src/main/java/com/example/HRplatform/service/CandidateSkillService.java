package com.example.HRplatform.service;

import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.repository.CandidateSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSkillService {

    @Autowired
    CandidateSkillRepository candidateSkillRepository;

    public void save(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }

    List<CandidateSkill> findByCandidateId(Long id) {
        return candidateSkillRepository.findByCandidateId(id);
    }

    public void delete(CandidateSkill candidateSkill) {
        candidateSkillRepository.delete(candidateSkill);
    }
}
