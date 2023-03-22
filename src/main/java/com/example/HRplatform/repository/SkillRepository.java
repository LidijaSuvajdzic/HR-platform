package com.example.HRplatform.repository;

import com.example.HRplatform.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);

    @Query(value = "SELECT s.id, s.name FROM skill s " +
            "JOIN candidate_skill cs ON s.id = cs.skill_id " +
            "JOIN candidate c ON c.id = cs.candidate_id " +
            "WHERE c.id = ?1", nativeQuery = true)
    List<Skill> findAllByCandidateId(Long candidateId);
}
