package com.example.HRplatform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CandidateSkillId.class)
public class CandidateSkill {

    @Id
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

}
