package com.example.HRplatform.service;

import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.repository.CandidateSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateSkillService {

    @Autowired
    CandidateSkillRepository candidateSkillRepository;

    public void save(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }
}
