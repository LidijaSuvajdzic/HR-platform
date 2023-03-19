package com.example.HRplatform.service;

import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.repository.CandidateSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateSkillService {

    private final CandidateSkillRepository candidateSkillRepository;

    public void save(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }

    List<CandidateSkill> findByCandidateId(Long id) {
        return candidateSkillRepository.findByCandidateId(id);
    }

    public void delete(CandidateSkill candidateSkill) {
        candidateSkillRepository.delete(candidateSkill);
    }

    public List<CandidateSkill> findBySkillId(Long id) {
        return candidateSkillRepository.findBySkillId(id);
    }

    public void delete(Long candidateId, Long skillId) {
        CandidateSkill candidateSkill = candidateSkillRepository.findByIds(candidateId,skillId);
        candidateSkillRepository.delete(candidateSkill);
    }

    public CandidateSkill findByIds(Long candidateId, Long skillId) {
        return candidateSkillRepository.findByIds(candidateId, skillId);
    }
}
