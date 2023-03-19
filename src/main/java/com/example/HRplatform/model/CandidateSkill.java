package com.example.HRplatform.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "skill_id")
    private Long skillId;

    public CandidateSkill(Long candidateId, Long skillId) {
        this.candidateId = candidateId;
        this.skillId = skillId;
    }
}
