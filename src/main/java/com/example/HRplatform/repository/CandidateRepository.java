package com.example.HRplatform.repository;

import com.example.HRplatform.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
}
