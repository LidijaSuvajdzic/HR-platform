package com.example.HRplatform.repository;

import com.example.HRplatform.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {


    Candidate save(Candidate candidate);

    @Query("SELECT c FROM Candidate c WHERE c.firstname = ?1 and c.lastname=?2")
    Optional<Candidate> findByFirstnameAndLastname(String firstname, String lastname);

    Optional<List<Candidate>> findByFirstname(String firstname);

    Optional<Candidate> findById(Long id);

    Optional<Candidate> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidate c WHERE c.email = ?1")
    boolean existsByEmail(String email);
}
