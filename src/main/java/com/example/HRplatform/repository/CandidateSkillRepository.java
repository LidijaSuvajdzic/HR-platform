package com.example.HRplatform.repository;

import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {

    Optional<CandidateSkill> findByCandidateAndSkill(Candidate candidate, Skill skill);

    List<CandidateSkill> findAllByCandidate(Candidate candidate);

    List<CandidateSkill> findAllBySkill(Skill skill);
}
