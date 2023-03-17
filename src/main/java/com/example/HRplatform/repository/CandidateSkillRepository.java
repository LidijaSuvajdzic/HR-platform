package com.example.HRplatform.repository;

import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {

    CandidateSkill save(CandidateSkill candidateSkill);

    @Query("SELECT cs FROM CandidateSkill cs WHERE cs.candidateId = ?1")
    List<CandidateSkill> findByCandidateId(Long id);

    @Query("SELECT cs FROM CandidateSkill cs WHERE cs.skillId = ?1")
    List<CandidateSkill> findBySkillId(Long id);
}
