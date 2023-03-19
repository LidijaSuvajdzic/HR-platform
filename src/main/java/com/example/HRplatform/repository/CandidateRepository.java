package com.example.HRplatform.repository;

import com.example.HRplatform.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUuid(UUID uuid);

    List<Candidate> findAllByName(String name);

    @Query(value = "SELECT DISTINCT c.id, c.uuid, c.name, c.email, c.phone_number, c.date_of_birth " +
            "FROM candidate c " +
            "JOIN candidate_skill cs ON c.id = cs.candidate_id " +
            "JOIN skill s ON s.id = cs.skill_id " +
            "WHERE s.name IN ?1", nativeQuery = true)
    List<Candidate> findAllBySkillNames(List<String> skillNames);
}
