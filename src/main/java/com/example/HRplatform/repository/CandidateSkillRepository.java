package com.example.HRplatform.repository;

import com.example.HRplatform.model.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {

    CandidateSkill save(CandidateSkill candidateSkill);
}
