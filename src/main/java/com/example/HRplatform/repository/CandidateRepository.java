package com.example.HRplatform.repository;

import com.example.HRplatform.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {


    Candidate save(Candidate candidate);

    @Query("SELECT c FROM Candidate c WHERE c.firstname = ?1 and c.lastname=?2")
    Candidate findByFirstNameAndLastName(String firstname, String lastname);
}
