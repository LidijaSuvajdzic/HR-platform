package com.example.HRplatform.model;


import jakarta.persistence.*;

@Entity
public class CandidateSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "skill_id")
    private Long skillId;

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public CandidateSkill(Long candidateId, Long skillId) {
        this.candidateId = candidateId;
        this.skillId = skillId;
    }
}
